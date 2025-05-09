package net.blay09.mods.littlejoys.mixin;

import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FishingHook.class)
public interface FishingHookAccessor {

    @Accessor
    int getTimeUntilLured();

    @Accessor
    void setTimeUntilLured(int timeUntilLured);

    @Accessor
    int getNibble();
}
