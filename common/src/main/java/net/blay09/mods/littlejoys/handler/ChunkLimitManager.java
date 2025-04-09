package net.blay09.mods.littlejoys.handler;

import net.blay09.mods.littlejoys.LittleJoys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class ChunkLimitManager extends SavedData {

    private static final String DATA_NAME = LittleJoys.MOD_ID;
    private static final String FISHING_SPOT_CHUNKS = "FishingSpotChunks";
    private static final String DIG_SPOT_CHUNKS = "DigSpotChunks";

    private final Map<Long, Integer> fishingSpotCounts = new HashMap<>();
    private final Map<Long, Integer> digSpotCounts = new HashMap<>();

    public int getTotalFishingSpotsInChunk(BlockPos pos) {
        final var chunkPos = new ChunkPos(pos);
        final var chunkKey = chunkPos.toLong();
        return fishingSpotCounts.getOrDefault(chunkKey, 0);
    }

    public int getTotalDigSpotsInChunk(BlockPos pos) {
        final var chunkPos = new ChunkPos(pos);
        final var chunkKey = chunkPos.toLong();
        return digSpotCounts.getOrDefault(chunkKey, 0);
    }

    public void trackFishingSpot(BlockPos pos) {
        final var chunkPos = new ChunkPos(pos);
        final var chunkKey = chunkPos.toLong();
        final var currentCount = fishingSpotCounts.getOrDefault(chunkKey, 0);
        fishingSpotCounts.put(chunkKey, currentCount + 1);
        setDirty();
    }

    public void trackDigSpot(BlockPos pos) {
        final var chunkPos = new ChunkPos(pos);
        final var chunkKey = chunkPos.toLong();
        final var currentCount = digSpotCounts.getOrDefault(chunkKey, 0);
        digSpotCounts.put(chunkKey, currentCount + 1);
        setDirty();
    }

    public static ChunkLimitManager read(CompoundTag tag, HolderLookup.Provider provider) {
        final var manager = new ChunkLimitManager();
        if (tag.contains(FISHING_SPOT_CHUNKS, Tag.TAG_COMPOUND)) {
            final var fishingSpotsTag = tag.getCompound(FISHING_SPOT_CHUNKS);
            for (final var key : fishingSpotsTag.getAllKeys()) {
                final var chunkKey = Long.parseLong(key);
                final var count = fishingSpotsTag.getInt(key);
                manager.fishingSpotCounts.put(chunkKey, count);
            }
        }

        if (tag.contains(DIG_SPOT_CHUNKS, Tag.TAG_COMPOUND)) {
            final var digSpotsTag = tag.getCompound(DIG_SPOT_CHUNKS);
            for (final var key : digSpotsTag.getAllKeys()) {
                final var chunkKey = Long.parseLong(key);
                final var count = digSpotsTag.getInt(key);
                manager.digSpotCounts.put(chunkKey, count);
            }
        }

        return manager;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        final var fishingSpotsTag = new CompoundTag();
        for (final var entry : fishingSpotCounts.entrySet()) {
            fishingSpotsTag.putInt(String.valueOf(entry.getKey()), entry.getValue());
        }
        tag.put(FISHING_SPOT_CHUNKS, fishingSpotsTag);

        final var digSpotsTag = new CompoundTag();
        for (final var entry : digSpotCounts.entrySet()) {
            digSpotsTag.putInt(String.valueOf(entry.getKey()), entry.getValue());
        }
        tag.put(DIG_SPOT_CHUNKS, digSpotsTag);

        return tag;
    }

    public static ChunkLimitManager get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new Factory<>(ChunkLimitManager::new, ChunkLimitManager::read, null), DATA_NAME);
    }
}