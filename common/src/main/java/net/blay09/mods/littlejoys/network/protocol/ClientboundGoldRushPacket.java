package net.blay09.mods.littlejoys.network.protocol;

import net.blay09.mods.littlejoys.client.handler.GoldRushClientHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public record ClientboundGoldRushPacket(BlockPos pos, boolean active) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ClientboundGoldRushPacket> TYPE = new CustomPacketPayload.Type<>(id("gold_rush"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundGoldRushPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ClientboundGoldRushPacket::pos,
            ByteBufCodecs.BOOL,
            ClientboundGoldRushPacket::active,
            ClientboundGoldRushPacket::new
    );

    public static void handle(final Player player, ClientboundGoldRushPacket message) {
        if (message.active) {
            GoldRushClientHandler.addActiveGoldRush(message.pos);
        } else {
            GoldRushClientHandler.removeActiveGoldRush(message.pos);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
