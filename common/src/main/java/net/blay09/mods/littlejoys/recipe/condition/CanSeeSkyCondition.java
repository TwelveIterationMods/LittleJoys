package net.blay09.mods.littlejoys.recipe.condition;

import com.mojang.serialization.MapCodec;
import net.blay09.mods.littlejoys.api.EventCondition;
import net.blay09.mods.littlejoys.api.EventContext;
import net.minecraft.network.FriendlyByteBuf;

public record CanSeeSkyCondition() implements EventCondition {

    public static final CanSeeSkyCondition INSTANCE = new CanSeeSkyCondition();
    public static final MapCodec<CanSeeSkyCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public boolean test(EventContext context) {
        return context.level().canSeeSky(context.pos());
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
    }

    public static CanSeeSkyCondition fromNetwork(FriendlyByteBuf buf) {
        return INSTANCE;
    }
}
