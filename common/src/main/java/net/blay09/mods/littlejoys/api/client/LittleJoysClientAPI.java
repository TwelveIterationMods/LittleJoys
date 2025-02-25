package net.blay09.mods.littlejoys.api.client;

import org.jetbrains.annotations.ApiStatus;

public class LittleJoysClientAPI {
    private static InternalClientMethods internalMethods;

    /**
     * Internal use only.
     */
    @ApiStatus.Internal
    public static void __setupAPI(InternalClientMethods internalMethods) {
        LittleJoysClientAPI.internalMethods = internalMethods;
    }

}
