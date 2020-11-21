package org.lua.minescript.minecraft.loaders.events;

import org.bukkit.Bukkit;
import org.lua.minescript.Main;
import org.lua.minescript.minecraft.events.player.E_PlayerJoin;
import org.lua.minescript.minecraft.events.player.E_PlayerQuit;

public class PlayerEventsLoader {
    public static void loader() {
        Main.pluginManager.registerEvents(new E_PlayerJoin(), Main.plugin);
        Main.pluginManager.registerEvents(new E_PlayerQuit(), Main.plugin);
    }
}
