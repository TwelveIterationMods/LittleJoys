package net.blay09.mods.littlejoys.client;

import net.blay09.mods.balm.api.client.rendering.BalmRenderers;
import net.blay09.mods.littlejoys.client.entity.DropRushItemRenderer;
import net.blay09.mods.littlejoys.entity.ModEntities;
import net.blay09.mods.littlejoys.particle.ModParticles;
import net.minecraft.client.particle.BubbleParticle;
import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.client.renderer.RenderType;
import net.blay09.mods.littlejoys.block.ModBlocks;
import net.minecraft.world.entity.EntityType;

public class ModRenderers {

    public static void initialize(BalmRenderers renderers) {
        // Note: To support cutout rendering on all loaders, you must additionally specify `"render_type": "minecraft:cutout"` in your block model JSON.
        renderers.setBlockRenderType(() -> ModBlocks.digSpot, RenderType.cutout());
        renderers.setBlockRenderType(() -> ModBlocks.fishingSpot, RenderType.cutout());

        renderers.registerEntityRenderer(() -> (EntityType) ModEntities.dropRushItem.get(), DropRushItemRenderer::new);

        renderers.registerParticleProvider(() -> ModParticles.goldRush, SuspendedTownParticle.HappyVillagerProvider::new);
        renderers.registerParticleProvider(() -> ModParticles.fishingSpot, SuspendedTownParticle.HappyVillagerProvider::new);
    }
}
