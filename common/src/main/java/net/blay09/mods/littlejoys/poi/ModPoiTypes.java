package net.blay09.mods.littlejoys.poi;

import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.world.BalmWorldGen;
import net.blay09.mods.littlejoys.block.ModBlocks;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.Set;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public class ModPoiTypes {

    public static DeferredObject<PoiType> digSpot;
    public static DeferredObject<PoiType> fishingSpot;

    public static void initialize(BalmWorldGen worldGen) {
        digSpot = worldGen.registerPoiType(id("dig_spot"), () -> new PoiType(Set.of(ModBlocks.digSpot.defaultBlockState()), 1, 1));
        fishingSpot = worldGen.registerPoiType(id("fishing_spot"), () -> new PoiType(Set.of(ModBlocks.fishingSpot.defaultBlockState()), 1, 1));
    }
}
