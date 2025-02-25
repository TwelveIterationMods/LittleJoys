package net.blay09.mods.littlejoys.particle;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.particle.BalmParticles;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public class ModParticles {
    public static SimpleParticleType goldRush;
    public static SimpleParticleType fishingSpot;

    public static void initialize(BalmParticles particles) {
        Balm.getRegistries().register(BuiltInRegistries.PARTICLE_TYPE, (identifier) -> goldRush = particles.createSimple(true), id("gold_rush"));
        Balm.getRegistries().register(BuiltInRegistries.PARTICLE_TYPE, (identifier) -> fishingSpot = particles.createSimple(true), id("fishing_spot"));
    }
}
