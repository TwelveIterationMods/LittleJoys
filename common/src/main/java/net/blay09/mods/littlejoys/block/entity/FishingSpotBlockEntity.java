package net.blay09.mods.littlejoys.block.entity;

import net.blay09.mods.balm.common.BalmBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;

public class FishingSpotBlockEntity extends BalmBlockEntity {

    private ResourceKey<Recipe<?>> recipeId;

    public FishingSpotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.fishingSpot.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        if (recipeId != null) {
            tag.putString("recipe", recipeId.toString());
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        if (tag.contains("recipe", Tag.TAG_STRING)) {
            recipeId = ResourceKey.create(Registries.RECIPE, ResourceLocation.parse(tag.getString("recipe")));
        }
    }

    public ResourceKey<Recipe<?>> getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(ResourceKey<Recipe<?>> recipeId) {
        this.recipeId = recipeId;
    }
}
