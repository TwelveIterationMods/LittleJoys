package net.blay09.mods.littlejoys.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.blay09.mods.littlejoys.block.ModBlocks;
import net.blay09.mods.littlejoys.tag.ModBlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider<Block> {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BLOCK, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(ModBlockTags.DIG_SPOTS).add(ModBlocks.digSpot);
        getOrCreateTagBuilder(ModBlockTags.FISHING_SPOTS).add(ModBlocks.fishingSpot);
    }
}
