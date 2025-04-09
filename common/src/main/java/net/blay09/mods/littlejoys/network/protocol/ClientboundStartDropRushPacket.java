package net.blay09.mods.littlejoys.network.protocol;

import net.blay09.mods.littlejoys.client.handler.DropRushClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public record ClientboundStartDropRushPacket(int ticks) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ClientboundStartDropRushPacket> TYPE = new CustomPacketPayload.Type<>(id("start_drop_rush"));

    public static void encode(FriendlyByteBuf buf, ClientboundStartDropRushPacket message) {
        buf.writeInt(message.ticks);
    }

    public static ClientboundStartDropRushPacket decode(FriendlyByteBuf buf) {
        return new ClientboundStartDropRushPacket(buf.readInt());
    }

    public static void handle(final Player player, ClientboundStartDropRushPacket message) {
        DropRushClientHandler.startDropRush(message.ticks);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
