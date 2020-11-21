package org.lua.minescript.minecraft.loaders;

import org.lua.minescript.minecraft.loaders.events.BlockEventsLoader;
import org.lua.minescript.minecraft.loaders.events.PlayerEventsLoader;

public class MainMinecraftLoader {
    public static void loader() {
        PlayerEventsLoader.loader();
        BlockEventsLoader.loader();
    }
}
