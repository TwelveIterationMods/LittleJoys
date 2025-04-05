package net.blay09.mods.littlejoys.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface EventContext {
    Level level();
    BlockPos pos();
    BlockState state();
}
