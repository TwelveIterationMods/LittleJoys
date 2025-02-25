package net.blay09.mods.littlejoys.client;

import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.littlejoys.client.handler.DropRushClientHandler;
import net.blay09.mods.littlejoys.client.handler.GoldRushClientHandler;

public class LittleJoysClient {
    public static void initialize() {
        ModRenderers.initialize(BalmClient.getRenderers());

        DropRushClientHandler.initialize();
        GoldRushClientHandler.initialize();
    }
}
