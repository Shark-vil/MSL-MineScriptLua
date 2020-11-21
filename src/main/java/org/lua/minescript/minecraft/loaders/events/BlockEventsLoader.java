package org.lua.minescript.minecraft.loaders.events;

import org.bukkit.Bukkit;
import org.lua.minescript.Main;
import org.lua.minescript.minecraft.events.block.E_BlockBreak;
import org.lua.minescript.minecraft.events.block.E_BlockPlace;

public class BlockEventsLoader {
    public static void loader() {
        Main.pluginManager.registerEvents(new E_BlockBreak(), Main.plugin);
        Main.pluginManager.registerEvents(new E_BlockPlace(), Main.plugin);
    }
}
