package net.blay09.mods.littlejoys.block;

import net.blay09.mods.balm.api.block.BalmBlocks;
import net.blay09.mods.balm.api.item.BalmItems;
import net.minecraft.world.level.block.Block;

import static net.blay09.mods.balm.api.block.BalmBlocks.blockProperties;
import static net.blay09.mods.littlejoys.LittleJoys.id;

public class ModBlocks {

    public static Block digSpot;
    public static Block fishingSpot;

    public static void initialize(BalmBlocks blocks) {
        blocks.register(
                (identifier) -> digSpot = new DigSpotBlock(blockProperties(identifier)),
                BalmItems::blockItem,
                id("dig_spot"));
        blocks.register(
                (identifier) -> fishingSpot = new FishingSpotBlock(blockProperties(identifier).noLootTable()),
                BalmItems::blockItem,
                id("fishing_spot"));
    }

}
