package net.blay09.mods.littlejoys.api;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public interface InternalMethods {
    <T extends EventCondition> void registerEventCondition(ResourceLocation identifier, Class<T> clazz, MapCodec<T> codec, Function<FriendlyByteBuf, T> networkDeserializer);
}
