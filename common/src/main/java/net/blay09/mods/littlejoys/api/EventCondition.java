package net.blay09.mods.littlejoys.api;

import com.mojang.serialization.Codec;
import net.blay09.mods.littlejoys.recipe.condition.EventConditionRegistry;
import net.minecraft.network.FriendlyByteBuf;

public interface EventCondition {
    boolean test(EventContext context);
    void toNetwork(FriendlyByteBuf buf);
}
