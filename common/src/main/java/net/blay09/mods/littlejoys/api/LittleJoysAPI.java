package net.blay09.mods.littlejoys.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

public class LittleJoysAPI {
    public static final String MOD_ID = "littlejoys";

    private static InternalMethods internalMethods;

    /**
     * Internal use only.
     */
    @ApiStatus.Internal
    public static void __setupAPI(InternalMethods internalMethods) {
        LittleJoysAPI.internalMethods = internalMethods;
    }

    public static <T extends EventCondition> void registerEventCondition(ResourceLocation identifier, Class<T> clazz, MapCodec<T> codec, Function<FriendlyByteBuf, T> networkDeserializer) {
        internalMethods.registerEventCondition(identifier, clazz, codec, networkDeserializer);
    }
}
