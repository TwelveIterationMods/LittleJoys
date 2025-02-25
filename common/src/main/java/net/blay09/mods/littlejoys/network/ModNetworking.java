package net.blay09.mods.littlejoys.network;

import net.blay09.mods.balm.api.network.BalmNetworking;
import net.blay09.mods.balm.api.network.SyncConfigMessage;
import net.blay09.mods.littlejoys.LittleJoysConfig;
import net.blay09.mods.littlejoys.network.protocol.ClientboundConfigMessage;
import net.blay09.mods.littlejoys.network.protocol.ClientboundStartDropRushPacket;
import net.blay09.mods.littlejoys.network.protocol.ClientboundGoldRushPacket;
import net.blay09.mods.littlejoys.network.protocol.ClientboundStopDropRushPacket;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public class ModNetworking {

    public static void initialize(BalmNetworking networking) {
        networking.registerClientboundPacket(id("gold_rush"),
                ClientboundGoldRushPacket.class,
                ClientboundGoldRushPacket::encode,
                ClientboundGoldRushPacket::decode,
                ClientboundGoldRushPacket::handle);

        networking.registerClientboundPacket(id("start_drop_rush"),
                ClientboundStartDropRushPacket.class,
                ClientboundStartDropRushPacket::encode,
                ClientboundStartDropRushPacket::decode,
                ClientboundStartDropRushPacket::handle);

        networking.registerClientboundPacket(id("stop_drop_rush"),
                ClientboundStopDropRushPacket.class,
                ClientboundStopDropRushPacket::encode,
                ClientboundStopDropRushPacket::decode,
                ClientboundStopDropRushPacket::handle);

        SyncConfigMessage.register(id("config"),
                ClientboundConfigMessage.class,
                ClientboundConfigMessage::new,
                LittleJoysConfig.class,
                LittleJoysConfig::new);
    }
}
