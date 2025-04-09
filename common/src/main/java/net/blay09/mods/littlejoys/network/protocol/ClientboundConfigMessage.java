package net.blay09.mods.littlejoys.network.protocol;

import net.blay09.mods.balm.api.network.SyncConfigMessage;
import net.blay09.mods.littlejoys.LittleJoysConfig;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public class ClientboundConfigMessage extends SyncConfigMessage<LittleJoysConfig> {
    public static final Type<ClientboundConfigMessage> TYPE = new Type<>(id("config"));

    public ClientboundConfigMessage(LittleJoysConfig littleJoysConfig) {
        super(TYPE, littleJoysConfig);
    }
}
