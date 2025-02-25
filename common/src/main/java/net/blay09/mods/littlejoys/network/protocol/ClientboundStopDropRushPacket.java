package net.blay09.mods.littlejoys.network.protocol;

import net.blay09.mods.littlejoys.client.handler.DropRushClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record ClientboundStopDropRushPacket(Reason reason) {

    public enum Reason {
        TIME_UP,
        FULL_CLEAR
    }

    public static void encode(ClientboundStopDropRushPacket message, FriendlyByteBuf buf) {
        buf.writeEnum(message.reason);
    }

    public static ClientboundStopDropRushPacket decode(FriendlyByteBuf buf) {
        return new ClientboundStopDropRushPacket(buf.readEnum(Reason.class));
    }

    public static void handle(final Player player, ClientboundStopDropRushPacket message) {
        DropRushClientHandler.stopDropRush(message.reason);
    }

}
