package net.blay09.mods.littlejoys.forge;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.balm.api.client.BalmClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.blay09.mods.littlejoys.LittleJoys;
import net.blay09.mods.littlejoys.client.LittleJoysClient;

@Mod(LittleJoys.MOD_ID)
public class ForgeLittleJoys {

    public ForgeLittleJoys(FMLJavaModLoadingContext context) {
        Balm.initialize(LittleJoys.MOD_ID, EmptyLoadContext.INSTANCE, LittleJoys::initialize);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> BalmClient.initialize(LittleJoys.MOD_ID, EmptyLoadContext.INSTANCE, LittleJoysClient::initialize));
    }

}
