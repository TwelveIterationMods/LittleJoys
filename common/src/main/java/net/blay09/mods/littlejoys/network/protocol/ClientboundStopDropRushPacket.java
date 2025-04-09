package net.blay09.mods.littlejoys.network.protocol;

import net.blay09.mods.littlejoys.client.handler.DropRushClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public record ClientboundStopDropRushPacket(Reason reason) implements CustomPacketPayload {

    public static final Type<ClientboundStopDropRushPacket> TYPE = new Type<>(id("stop_drop_rush"));

    public enum Reason {
        TIME_UP,
        FULL_CLEAR
    }

    public static void encode(FriendlyByteBuf buf, ClientboundStopDropRushPacket message) {
        buf.writeEnum(message.reason);
    }

    public static ClientboundStopDropRushPacket decode(FriendlyByteBuf buf) {
        return new ClientboundStopDropRushPacket(buf.readEnum(Reason.class));
    }

    public static void handle(final Player player, ClientboundStopDropRushPacket message) {
        DropRushClientHandler.stopDropRush(message.reason);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
