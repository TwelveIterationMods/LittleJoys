package net.blay09.mods.littlejoys.block;

import com.mojang.serialization.MapCodec;
import net.blay09.mods.littlejoys.block.entity.DigSpotBlockEntity;
import net.blay09.mods.littlejoys.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DigSpotBlock extends BaseEntityBlock {

    public static final MapCodec<DigSpotBlock> CODEC = simpleCodec(DigSpotBlock::new);

    private static final VoxelShape SHAPE = Shapes.empty();

    public DigSpotBlock(Properties properties) {
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
        return Block.isFaceFull(state.getCollisionShape(level, pos), Direction.UP);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DigSpotBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.5f) {
            final var x = pos.getX() + 0.5f;
            final var y = pos.getY() + 0.05f;
            final var z = pos.getZ() + 0.5f;
            final var offsetX = 0.1f - random.nextFloat() * 0.2f;
            final var offsetZ = 0.1f - random.nextFloat() * 0.2f;
            level.addParticle(ModParticles.goldRush, x + offsetX, y, z + offsetZ, 0f, 0f, 0f);
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
