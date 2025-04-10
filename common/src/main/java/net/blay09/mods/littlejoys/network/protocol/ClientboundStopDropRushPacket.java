package net.blay09.mods.littlejoys.network.protocol;

import io.netty.buffer.ByteBuf;
import net.blay09.mods.littlejoys.client.handler.DropRushClientHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;

import java.util.Locale;
import java.util.function.IntFunction;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public record ClientboundStopDropRushPacket(Reason reason) implements CustomPacketPayload {

    public static final Type<ClientboundStopDropRushPacket> TYPE = new Type<>(id("stop_drop_rush"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundStopDropRushPacket> STREAM_CODEC = StreamCodec.composite(
            Reason.STREAM_CODEC,
            ClientboundStopDropRushPacket::reason,
            ClientboundStopDropRushPacket::new
    );

    public static void handle(final Player player, ClientboundStopDropRushPacket message) {
        DropRushClientHandler.stopDropRush(message.reason);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum Reason implements StringRepresentable {
        TIME_UP,
        FULL_CLEAR;

        private static final IntFunction<Reason> BY_ID = ByIdMap.continuous(Reason::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, Reason> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Reason::ordinal);

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
