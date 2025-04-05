package net.blay09.mods.littlejoys;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.config.BalmConfigData;
import net.blay09.mods.balm.api.config.Comment;
import net.blay09.mods.balm.api.config.Config;
import net.blay09.mods.littlejoys.network.protocol.ClientboundConfigMessage;

@Config(LittleJoys.MOD_ID)
public class LittleJoysConfig implements BalmConfigData {

    public static void initialize() {
        Balm.getConfig().registerConfig(LittleJoysConfig.class, ClientboundConfigMessage::new);
    }

    public static LittleJoysConfig getActive() {
        return Balm.getConfig().getActive(LittleJoysConfig.class);
    }

    public DropRush dropRush = new DropRush();
    public GoldRush goldRush = new GoldRush();
    public FishingSpots fishingSpots = new FishingSpots();
    public DigSpots digSpots = new DigSpots();

    public static class FishingSpots {
        @Comment("The minimum distance between fishing spots, preventing them from spawning too close together.")
        public int minimumDistanceBetween = 128;

        @Comment("The distance fishing spots will spawn from the player.")
        public int spawnDistance = 8;

        @Comment("The seconds that must pass after a fishing spot appeared before another fishing spot can appear for a player.")
        public float spawnIntervalSeconds = 300;

        @Comment("The seconds that must pass after fishing a fishing spot before another fishing spot can appear for a player.")
        public float afterFishingCooldownSeconds = 600;

        @Comment("The maximum distance a bobber can be from a fishing spot to still trigger it.")
        public int fishingRangeTolerance = 3;

        @Comment("Fishing spots will lure fish quicker than regular water. Set to -1 to disable.")
        public float secondsUntilLured = 2f;

        @Comment("The limit of fishing spots that can spawn in a given chunk overall. Once this many fishing spots have spawned, the chunk will never spawn any again. Set to -1 to disable.")
        public int totalLimitPerChunk = -1;

        @Comment("The offset applied to the spawn area in the direction the player is facing. Set to 0 to center it around the player.")
        public int projectForwardDistance = 4;
    }

    public static class DigSpots {
        @Comment("The minimum distance between dig spots, preventing them from spawning too close together.")
        public int minimumDistanceBetween = 128;

        @Comment("The distance dig spots will spawn from the player.")
        public int spawnDistance = 32;

        @Comment("The seconds that must pass after a dig spot appeared before another dig spot can appear for a player.")
        public float spawnIntervalSeconds = 300;

        @Comment("The seconds that must pass after digging a dig spot before another dig spot can appear for a player.")
        public float afterDiggingCooldownSeconds = 600;

        @Comment("The limit of dig spots that can spawn in a given chunk overall. Once this many dig spots have spawned, the chunk will never spawn any again. Set to -1 to disable.")
        public int totalLimitPerChunk = 1;

        @Comment("The offset applied to the spawn area in the direction the player is facing. Set to 0 to center it around the player.")
        public int projectForwardDistance = 25;
    }

    public static class DropRush {
        @Comment("The base chance for a drop rush to occur.")
        public float baseChance = 0.02f;
    }

    public static class GoldRush {
        @Comment("The base chance for a gold rush to occur.")
        public float baseChance = 0.02f;
    }
}
