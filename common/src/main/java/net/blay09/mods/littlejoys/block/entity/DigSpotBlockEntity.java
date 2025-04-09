package net.blay09.mods.littlejoys.block.entity;

import net.blay09.mods.balm.api.block.entity.OnLoadHandler;
import net.blay09.mods.balm.common.BalmBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class DigSpotBlockEntity extends BalmBlockEntity implements OnLoadHandler {

    private ResourceLocation recipeId;
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
        if (recipeId != null) {
            tag.putString("recipe", recipeId.toString());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        if (tag.contains("recipe", Tag.TAG_STRING)) {
            recipeId = ResourceLocation.parse(tag.getString("recipe"));
        }
    }

    public ResourceLocation getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(ResourceLocation recipeId) {
        this.recipeId = recipeId;
    }
}
