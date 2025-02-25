package net.blay09.mods.littlejoys.block.entity;

import net.blay09.mods.balm.api.block.entity.OnLoadHandler;
import net.blay09.mods.balm.common.BalmBlockEntity;
import net.minecraft.core.BlockPos;
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
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (recipeId != null) {
            tag.putString("recipe", recipeId.toString());
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("recipe", Tag.TAG_STRING)) {
            recipeId = new ResourceLocation(tag.getString("recipe"));
        }
    }

    public ResourceLocation getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(ResourceLocation recipeId) {
        this.recipeId = recipeId;
    }
}
