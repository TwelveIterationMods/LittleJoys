package net.blay09.mods.littlejoys.sound;

import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.sound.BalmSounds;
import net.minecraft.sounds.SoundEvent;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public class ModSounds {
    public static DeferredObject<SoundEvent> goldRush;
    public static DeferredObject<SoundEvent> dropRushStart;
    public static DeferredObject<SoundEvent> dropRush;
    public static DeferredObject<SoundEvent> dropRushStop;

    public static void initialize(BalmSounds sounds) {
        goldRush = sounds.register(id("gold_rush"));
        dropRushStart = sounds.register(id("drop_rush_start"));
        dropRush = sounds.register(id("drop_rush"));
        dropRushStop = sounds.register(id("drop_rush_stop"));
    }
}
