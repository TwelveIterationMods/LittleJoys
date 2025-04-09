package net.blay09.mods.littlejoys.loot;

import net.blay09.mods.balm.api.loot.BalmLootModifier;
import net.blay09.mods.littlejoys.block.entity.DigSpotBlockEntity;
import net.blay09.mods.littlejoys.handler.DigSpotHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DigSpotLootModifier implements BalmLootModifier {

    private static final Set<LootContext> activeContexts = new HashSet<>();

    @Override
    public void apply(LootContext context, List<ItemStack> list) {
        synchronized (activeContexts) {
            if (activeContexts.contains(context)) {
                return;
            }
        }

        final var level = context.getLevel();
        final var vec = context.getParamOrNull(LootContextParams.ORIGIN);
        final var state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        final var blockEntity = context.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (state == null || vec == null || !(blockEntity instanceof DigSpotBlockEntity digSpot)) {
            return;
        }

        DigSpotHandler.recipeById(level, digSpot.getRecipeId()).ifPresent(recipe -> {
            final var lootTableId = recipe.lootTable();
            if (lootTableId != BuiltInLootTables.EMPTY) {
                final var lootTable = level.getServer().reloadableRegistries().getLootTable(lootTableId);
                synchronized (activeContexts) {
                    activeContexts.add(context);
                }
                lootTable.getRandomItems(context, list::add);
                synchronized (activeContexts) {
                    activeContexts.remove(context);
                }
            }
        });
    }

}
