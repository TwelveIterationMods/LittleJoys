package net.blay09.mods.littlejoys.fabric;

import net.blay09.mods.balm.api.Balm;
import net.fabricmc.api.ModInitializer;
import net.blay09.mods.littlejoys.LittleJoys;

public class FabricLittleJoys implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initialize(LittleJoys.MOD_ID, LittleJoys::initialize);
    }
}
