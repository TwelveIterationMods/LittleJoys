package net.blay09.mods.littlejoys.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public final class GoldRushInstance {
    private final BlockPos pos;
    private final BlockState initialState;
    private final ResourceLocation lootTable;
    private final int maxTicks;
    private final int ticksPerDrop;
    private int ticksPassed;
    private int dropCooldownTicks;

    public GoldRushInstance(BlockPos pos, BlockState initialState, ResourceLocation lootTable, int maxTicks, int ticksPerDrop) {
        this.pos = pos;
        this.initialState = initialState;
        this.lootTable = lootTable;
        this.maxTicks = maxTicks;
        this.ticksPerDrop = ticksPerDrop;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getInitialState() {
        return initialState;
    }

    public ResourceLocation getLootTable() {
        return lootTable;
    }

    public int getMaxTicks() {
        return maxTicks;
    }

    public int getTicksPerDrop() {
        return ticksPerDrop;
    }

    public int getTicksPassed() {
        return ticksPassed;
    }

    public void setTicksPassed(int ticksPassed) {
        this.ticksPassed = ticksPassed;
    }

    public int getDropCooldownTicks() {
        return dropCooldownTicks;
    }

    public void setDropCooldownTicks(int dropCooldownTicks) {
        this.dropCooldownTicks = dropCooldownTicks;
    }
}
