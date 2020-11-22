package org.lua.minescript.minecraft.loaders;

import org.lua.minescript.minecraft.events.java.cache.JE_Cache;
import org.lua.minescript.minecraft.events.java.hooks.JE_UpdateHook;
import org.lua.minescript.minecraft.events.java.timer.JE_Timer;
import org.lua.minescript.minecraft.loaders.events.BlockEventsLoader;
import org.lua.minescript.minecraft.loaders.events.PlayerEventsLoader;

public class MainMinecraftLoader {
    public static void loader() {
        PlayerEventsLoader.loader();
        BlockEventsLoader.loader();

        JE_UpdateHook.Register();
        JE_Timer.Register();
        JE_Cache.RegisterCleaner();
    }
}
