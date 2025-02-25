package net.blay09.mods.littlejoys.network.protocol;

import net.blay09.mods.balm.api.network.SyncConfigMessage;
import net.blay09.mods.littlejoys.LittleJoysConfig;

public class ClientboundConfigMessage extends SyncConfigMessage<LittleJoysConfig> {
    public ClientboundConfigMessage(LittleJoysConfig littleJoysConfig) {
        super(littleJoysConfig);
    }
}
