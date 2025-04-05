package net.blay09.mods.littlejoys.block;

import net.blay09.mods.littlejoys.block.entity.FishingSpotBlockEntity;
import net.blay09.mods.littlejoys.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FishingSpotBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE = Shapes.empty();

    public FishingSpotBlock(Properties properties) {
        super(properties.replaceable());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        final var posBelow = pos.below();
        return mayPlaceOn(level.getBlockState(posBelow), level, posBelow);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        final var fluidState = level.getFluidState(pos);
        return fluidState.is(FluidTags.WATER) && fluidState.isSource();
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        final var x = pos.getX() + 0.5f;
        final var y = pos.getY() - 0.1f;
        final var z = pos.getZ() + 0.5f;
        final var offsetX = 0.2f - random.nextFloat() * 0.4f;
        final var offsetZ = 0.2f - random.nextFloat() * 0.4f;
        level.addAlwaysVisibleParticle(ModParticles.fishingSpot, x + offsetX, y, z + offsetZ, 0f, 0f, 0f);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FishingSpotBlockEntity(pos, state);
    }
}
