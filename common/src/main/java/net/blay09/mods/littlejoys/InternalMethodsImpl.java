package net.blay09.mods.littlejoys;

import com.mojang.serialization.MapCodec;
import net.blay09.mods.littlejoys.api.EventCondition;
import net.blay09.mods.littlejoys.api.InternalMethods;
import net.blay09.mods.littlejoys.recipe.condition.EventConditionRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class InternalMethodsImpl implements InternalMethods {
    @Override
    public <T extends EventCondition> void registerEventCondition(ResourceLocation identifier, Class<T> clazz, MapCodec<T> codec, Function<FriendlyByteBuf, T> networkDeserializer) {
        EventConditionRegistry.registerCondition(identifier, clazz, codec, networkDeserializer);
    }
}
