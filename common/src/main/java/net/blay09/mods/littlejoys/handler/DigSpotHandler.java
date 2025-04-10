package net.blay09.mods.littlejoys.handler;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.TickPhase;
import net.blay09.mods.balm.api.event.TickType;
import net.blay09.mods.littlejoys.LittleJoys;
import net.blay09.mods.littlejoys.LittleJoysConfig;
import net.blay09.mods.littlejoys.block.ModBlocks;
import net.blay09.mods.littlejoys.block.entity.DigSpotBlockEntity;
import net.blay09.mods.littlejoys.mixin.RecipeManagerAccessor;
import net.blay09.mods.littlejoys.recipe.DigSpotRecipe;
import net.blay09.mods.littlejoys.recipe.ModRecipeTypes;
import net.blay09.mods.littlejoys.recipe.condition.EventContextImpl;
import net.blay09.mods.littlejoys.stats.ModStats;
import net.blay09.mods.littlejoys.tag.ModPoiTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;

public class DigSpotHandler {

    private static final RandomSource random = RandomSource.create();
    private static final String DIG_SPOT_COOLDOWN = "digSpotCooldown";

    public static void initialize() {
        Balm.getEvents().onTickEvent(TickType.ServerPlayer, TickPhase.End, (player) -> {
            final var playerData = Balm.getHooks().getPersistentData(player);
            final var littleJoysData = playerData.getCompoundOrEmpty(LittleJoys.MOD_ID);
            playerData.put(LittleJoys.MOD_ID, littleJoysData);
            final var cooldown = littleJoysData.getIntOr(DIG_SPOT_COOLDOWN, 0);
            if (cooldown > 0) {
                littleJoysData.putInt(DIG_SPOT_COOLDOWN, cooldown - 1);
            } else {
                final var level = (ServerLevel) player.level();
                final var poiManager = level.getPoiManager();
                final var centerPos = getOriginForNextSpawn(player);
                final var checkRange = LittleJoysConfig.getActive().digSpots.minimumDistanceBetween;
                final var spawnRange = LittleJoysConfig.getActive().digSpots.spawnDistance;
                final var closestDigSpot = poiManager.findClosest(it -> it.is(ModPoiTypeTags.DIG_SPOTS), centerPos, checkRange, PoiManager.Occupancy.ANY);
                if (closestDigSpot.isEmpty()) {
                    final var surfacePos = getVerticallyNearRandomOffsetPos(level, centerPos, spawnRange);
                    final var aboveSurfacePos = surfacePos.above();

                    final var totalSpots = ChunkLimitManager.get(level).getTotalDigSpotsInChunk(aboveSurfacePos);
                    final var maxSpots = LittleJoysConfig.getActive().digSpots.totalLimitPerChunk;
                    if (maxSpots > 0 && totalSpots >= maxSpots) {
                        return;
                    }

                    if (!level.getBlockState(aboveSurfacePos).canBeReplaced()) {
                        return;
                    }

                    findRecipe(level, aboveSurfacePos).ifPresentOrElse(recipeHolder -> {
                        level.setBlock(aboveSurfacePos, ModBlocks.digSpot.defaultBlockState(), 3);
                        if (level.getBlockEntity(aboveSurfacePos) instanceof DigSpotBlockEntity digSpot) {
                            digSpot.setRecipeId(recipeHolder.id());
                        }
                        ChunkLimitManager.get(level).trackDigSpot(aboveSurfacePos);
                        littleJoysData.putInt(DIG_SPOT_COOLDOWN, Math.round(LittleJoysConfig.getActive().digSpots.spawnIntervalSeconds * 20));
                    }, () -> littleJoysData.putInt(DIG_SPOT_COOLDOWN, 20));
                }
            }
        });
    }

    private static BlockPos getOriginForNextSpawn(Player player) {
        final var projectForwardDistance = LittleJoysConfig.getActive().digSpots.projectForwardDistance;
        final var forwardDirection = player.getDirection();
        return player.blockPosition().relative(forwardDirection, projectForwardDistance);
    }

    private static BlockPos getVerticallyNearRandomOffsetPos(ServerLevel level, BlockPos origin, int spawnRange) {
        BlockPos bestPos = null;
        int bestDist = Integer.MAX_VALUE;
        for (int i = 0; i < 5; i++) {
            final var offsetX = random.nextInt(spawnRange + spawnRange) - spawnRange;
            final var offsetZ = random.nextInt(spawnRange + spawnRange) - spawnRange;
            final var randomOffsetPos = new BlockPos(origin.getX() + offsetX, origin.getX(), origin.getZ() + offsetZ);
            final var surfacePos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, randomOffsetPos).below();
            final var surfaceDist = Math.abs(surfacePos.getY() - origin.getY());
            if (bestPos == null || surfaceDist < bestDist) {
                bestPos = surfacePos;
                bestDist = surfaceDist;
            }
        }
        return bestPos;
    }

    private static Optional<RecipeHolder<DigSpotRecipe>> findRecipe(ServerLevel level, BlockPos pos) {
        final var recipeManager = level.getServer().getRecipeManager();
        final var recipeMap = ((RecipeManagerAccessor) recipeManager).getRecipes();
        final var recipes = recipeMap.byType(ModRecipeTypes.digSpotRecipeType);
        final var candidates = new ArrayList<RecipeHolder<DigSpotRecipe>>();
        for (final var recipe : recipes) {
            if (isValidRecipeFor(recipe, level, pos)) {
                candidates.add(recipe);
            }
        }
        return WeightedRandom.getRandomItem(random, candidates, it -> it.value().weight());
    }

    private static boolean isValidRecipeFor(RecipeHolder<DigSpotRecipe> recipe, ServerLevel level, BlockPos pos) {
        final var context = new EventContextImpl(level, pos, level.getBlockState(pos));
        return recipe.value().eventCondition().test(context);
    }

    public static Optional<DigSpotRecipe> recipeById(ServerLevel level, @Nullable ResourceKey<Recipe<?>> recipeId) {
        final var recipeManager = level.recipeAccess();
        if (recipeId == null) {
            return Optional.empty();
        }
        final var recipeHolder = recipeManager.byKey(recipeId).orElse(null);
        if (recipeHolder != null && recipeHolder.value() instanceof DigSpotRecipe digSpotRecipe) {
            return Optional.of(digSpotRecipe);
        }
        return Optional.empty();
    }

    public static void digSpotConsumed(Player player) {
        final var playerData = Balm.getHooks().getPersistentData(player);
        final var littleJoysData = playerData.getCompoundOrEmpty(LittleJoys.MOD_ID);
        playerData.put(LittleJoys.MOD_ID, littleJoysData);
        littleJoysData.putInt(DIG_SPOT_COOLDOWN, Math.round(LittleJoysConfig.getActive().digSpots.afterDiggingCooldownSeconds * 20));

        player.awardStat(ModStats.digSpotsDug);
    }
}
