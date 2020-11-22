package org.lua.minescript.minecraft.events.java.cache;

import org.bukkit.ChatColor;
import org.lua.minescript.Main;
import org.lua.minescript.converter.LuaConverter;
import org.lua.minescript.lua.libraries.hook.L_Hook;
import org.lua.minescript.models.hook.HookModel;
import org.lua.minescript.storage.cache.CacheStorage;

import java.util.ArrayList;
import java.util.List;

public class JE_Cache {
    public static void RegisterCleaner()
    {
        try {
            Main.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
                public void run() {
                    CacheStorage.Clear();
                }}, 0, 20L); // 1 second tick
        }
        catch (Throwable ex) {
            Main.console.sendMessage(ChatColor.RED + "Something is creating script error:\n" + ex);
        }
    }
}
