package net.blay09.mods.littlejoys.fabric.datagen;

import net.blay09.mods.littlejoys.poi.ModPoiTypes;
import net.blay09.mods.littlejoys.tag.ModPoiTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.concurrent.CompletableFuture;

public class ModPoiTypeTagProvider extends FabricTagProvider<PoiType> {
    public ModPoiTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(ModPoiTypeTags.DIG_SPOTS).add(ModPoiTypes.digSpot.get());
        getOrCreateTagBuilder(ModPoiTypeTags.FISHING_SPOTS).add(ModPoiTypes.fishingSpot.get());
    }
}
