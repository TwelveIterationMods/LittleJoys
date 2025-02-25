package net.blay09.mods.littlejoys.block.entity;

import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.block.BalmBlockEntities;
import net.blay09.mods.littlejoys.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public class ModBlockEntities {

    public static DeferredObject<BlockEntityType<DigSpotBlockEntity>> digSpot;
    public static DeferredObject<BlockEntityType<FishingSpotBlockEntity>> fishingSpot;

    public static void initialize(BalmBlockEntities blockEntities) {
        digSpot = blockEntities.registerBlockEntity(id("dig_spot"), DigSpotBlockEntity::new, () -> new Block[]{ModBlocks.digSpot});
        fishingSpot = blockEntities.registerBlockEntity(id("fishing_spot"), FishingSpotBlockEntity::new, () -> new Block[]{ModBlocks.fishingSpot});
    }
}
