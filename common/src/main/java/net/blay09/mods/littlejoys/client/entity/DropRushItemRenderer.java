package net.blay09.mods.littlejoys.client.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.blay09.mods.littlejoys.entity.DropRushItemEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.Objects;

public class DropRushItemRenderer extends ItemEntityRenderer {
    public DropRushItemRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ItemEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        final var dropRushItemEntity = (DropRushItemEntity) entity;
        final var player = Minecraft.getInstance().player;
        final var mine = player != null && Objects.equals(dropRushItemEntity.getTarget(), player.getUUID());
        if (mine) {
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        } else {
            RenderSystem.setShaderColor(1f, 1f, 1f, 0.25f);
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        }
    }
}
