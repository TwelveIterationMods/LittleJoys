package net.blay09.mods.littlejoys.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public class ModPoiTypeTags {
    public static final TagKey<PoiType> DIG_SPOTS = TagKey.create(Registries.POINT_OF_INTEREST_TYPE, id("dig_spots"));
    public static final TagKey<PoiType> FISHING_SPOTS = TagKey.create(Registries.POINT_OF_INTEREST_TYPE, id("fishing_spots"));
}
