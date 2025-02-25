package net.blay09.mods.littlejoys.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.blay09.mods.littlejoys.block.ModBlocks;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createNonTemplateModelBlock(ModBlocks.digSpot);
        blockStateModelGenerator.createNonTemplateModelBlock(ModBlocks.fishingSpot);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }

}
