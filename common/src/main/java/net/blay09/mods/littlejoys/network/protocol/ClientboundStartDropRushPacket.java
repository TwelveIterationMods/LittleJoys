package net.blay09.mods.littlejoys.network.protocol;

import net.blay09.mods.littlejoys.client.handler.DropRushClientHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public record ClientboundStartDropRushPacket(int ticks) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ClientboundStartDropRushPacket> TYPE = new CustomPacketPayload.Type<>(id("start_drop_rush"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundStartDropRushPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ClientboundStartDropRushPacket::ticks,
            ClientboundStartDropRushPacket::new
    );

    public static void handle(final Player player, ClientboundStartDropRushPacket message) {
        DropRushClientHandler.startDropRush(message.ticks);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
