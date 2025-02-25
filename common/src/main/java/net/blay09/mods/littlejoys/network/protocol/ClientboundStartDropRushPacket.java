package net.blay09.mods.littlejoys.network.protocol;

import net.blay09.mods.littlejoys.client.handler.DropRushClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record ClientboundStartDropRushPacket(int ticks) {

    public static void encode(ClientboundStartDropRushPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.ticks);
    }

    public static ClientboundStartDropRushPacket decode(FriendlyByteBuf buf) {
        return new ClientboundStartDropRushPacket(buf.readInt());
    }

    public static void handle(final Player player, ClientboundStartDropRushPacket message) {
        DropRushClientHandler.startDropRush(message.ticks);
    }

}
