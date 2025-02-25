package net.blay09.mods.littlejoys.recipe.condition;

import com.mojang.serialization.MapCodec;
import net.blay09.mods.littlejoys.api.EventCondition;
import net.blay09.mods.littlejoys.api.EventContext;
import net.minecraft.network.FriendlyByteBuf;

public record AlwaysCondition() implements EventCondition {

    public static final AlwaysCondition INSTANCE = new AlwaysCondition();
    public static final MapCodec<AlwaysCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public boolean test(EventContext context) {
        return true;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
    }

    public static AlwaysCondition fromNetwork(FriendlyByteBuf buf) {
        return INSTANCE;
    }
}
