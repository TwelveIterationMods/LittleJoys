package net.blay09.mods.littlejoys.block.entity;

import net.blay09.mods.balm.api.block.entity.OnLoadHandler;
import net.blay09.mods.balm.common.BalmBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;

public class DigSpotBlockEntity extends BalmBlockEntity implements OnLoadHandler {

    private ResourceKey<Recipe<?>> recipeId;
    private BlockState stateBelow;

    public DigSpotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.digSpot.get(), pos, state);
    }

    @Override
    public void onLoad() {
        if (level != null) {
            stateBelow = level.getBlockState(worldPosition.below());
        }
    }

    public BlockState getStateBelow() {
        return stateBelow;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        tag.storeNullable("recipe", ResourceKey.codec(Registries.RECIPE), recipeId);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        recipeId = tag.read("recipe", ResourceKey.codec(Registries.RECIPE)).orElse(null);
    }

    public ResourceKey<Recipe<?>> getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(ResourceKey<Recipe<?>> recipeId) {
        this.recipeId = recipeId;
    }
}
