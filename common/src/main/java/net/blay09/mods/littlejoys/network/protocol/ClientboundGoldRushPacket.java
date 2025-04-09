package net.blay09.mods.littlejoys.network.protocol;

import net.blay09.mods.littlejoys.client.handler.GoldRushClientHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public record ClientboundGoldRushPacket(BlockPos pos, boolean active) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ClientboundGoldRushPacket> TYPE = new CustomPacketPayload.Type<>(id("gold_rush"));

    public static void encode(FriendlyByteBuf buf, ClientboundGoldRushPacket message) {
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

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
