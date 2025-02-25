package net.blay09.mods.littlejoys.handler;

import net.minecraft.core.BlockPos;

import java.util.Optional;

public interface FishingSpotHolder {
    Optional<BlockPos> getFishingSpot();
    void setFishingSpot(BlockPos fishingSpot);
}
