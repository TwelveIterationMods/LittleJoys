package net.blay09.mods.littlejoys.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.blay09.mods.littlejoys.LittleJoysConfig;
import net.blay09.mods.littlejoys.api.EventCondition;
import net.blay09.mods.littlejoys.recipe.condition.EventConditionRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record GoldRushRecipe(ResourceLocation identifier,
                             EventCondition eventCondition,
                             float chanceMultiplier,
                             ResourceLocation lootTable,
                             float seconds,
                             float maxDropsPerSecond,
                             Weight weight) implements Recipe<Container>, WeightedEntry {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoldRushRecipe.class);

    @Override
    public RecipeType<GoldRushRecipe> getType() {
        return ModRecipeTypes.goldRushRecipeType;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return identifier;
    }

    @Override
    public RecipeSerializer<GoldRushRecipe> getSerializer() {
        return ModRecipeTypes.goldRushRecipeSerializer;
    }

    @Override
    public Weight getWeight() {
        return weight;
    }

    public static class Serializer implements RecipeSerializer<GoldRushRecipe> {
        @Override
        public GoldRushRecipe fromJson(ResourceLocation identifier, JsonObject jsonObject) {
            final var eventCondition = EventConditionRegistry.CODEC.decode(JsonOps.INSTANCE, GsonHelper.getNonNull(jsonObject, "eventCondition"))
                    .getOrThrow(false, LOGGER::error)
                    .getFirst();
            final var lootTable = new ResourceLocation(GsonHelper.getAsString(jsonObject, "lootTable"));
            final var chanceMultiplier = GsonHelper.getAsFloat(jsonObject, "chanceMultiplier", 1f);
            final var seconds = GsonHelper.getAsFloat(jsonObject, "seconds", 7f);
            final var maxDropsPerSecond = GsonHelper.getAsFloat(jsonObject, "maxDropsPerSecond", -1);
            final var weight = Weight.of(GsonHelper.getAsInt(jsonObject, "weight", 1));
            return new GoldRushRecipe(identifier, eventCondition, chanceMultiplier, lootTable, seconds, maxDropsPerSecond, weight);
        }

        @Override
        public GoldRushRecipe fromNetwork(ResourceLocation identifier, FriendlyByteBuf buf) {
            final var eventCondition = EventConditionRegistry.conditionFromNetwork(buf);
            final var chanceMultiplier = buf.readFloat();
            final var seconds = buf.readFloat();
            final var lootTable = buf.readResourceLocation();
            final var maxDropsPerSecond = buf.readFloat();
            final var weight = Weight.of(buf.readVarInt());
            return new GoldRushRecipe(identifier, eventCondition, chanceMultiplier, lootTable, seconds, maxDropsPerSecond, weight);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, GoldRushRecipe recipe) {
            EventConditionRegistry.conditionToNetwork(buf, recipe.eventCondition);
            buf.writeFloat(recipe.chanceMultiplier);
            buf.writeResourceLocation(recipe.lootTable);
            buf.writeFloat(recipe.seconds);
            buf.writeFloat(recipe.maxDropsPerSecond);
            buf.writeVarInt(recipe.weight.asInt());
        }
    }
}
