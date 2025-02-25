package net.blay09.mods.littlejoys.network.protocol;

import net.blay09.mods.littlejoys.client.handler.GoldRushClientHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record ClientboundGoldRushPacket(BlockPos pos, boolean active) {

    public static void encode(ClientboundGoldRushPacket message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.pos());
        buf.writeBoolean(message.active());
    }

    public static ClientboundGoldRushPacket decode(FriendlyByteBuf buf) {
        return new ClientboundGoldRushPacket(buf.readBlockPos(), buf.readBoolean());
    }

    public static void handle(final Player player, ClientboundGoldRushPacket message) {
        if (message.active) {
            GoldRushClientHandler.addActiveGoldRush(message.pos);
        } else {
            GoldRushClientHandler.removeActiveGoldRush(message.pos);
        }
    }

}
