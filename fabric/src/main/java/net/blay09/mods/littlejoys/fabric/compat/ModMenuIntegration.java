package net.blay09.mods.littlejoys.fabric.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.blay09.mods.balm.fabric.compat.ModMenuUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.blay09.mods.littlejoys.LittleJoysConfig;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModMenuUtils.getConfigScreen(LittleJoysConfig.class);
    }
}
