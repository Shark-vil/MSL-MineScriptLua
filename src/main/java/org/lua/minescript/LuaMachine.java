package org.lua.minescript;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.lua.minescript.lua.loaders.MainLuaLoader;
import org.lua.minescript.managers.lua.ScriptManager;
import org.lua.minescript.managers.watcher.WatcherManager;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaMachine {
    public static final Globals vm = JsePlatform.standardGlobals();

    public LuaMachine(JavaPlugin plugin) {
        MainLuaLoader.load();

        // Script search
        ScriptManager.LoadAll();
        // Using scripts
        for(ScriptManager.ScriptModel n_Script : ScriptManager.AutorunScripts) {
            Main.console.sendMessage(ChatColor.GREEN + " - " + n_Script.Path);
            ScriptManager.ScriptExecute(n_Script.Path);
        }

        // Activating a directory watcher
        WatcherManager.EnableWatcher();
        // Parsing folders for the watcher
        for (String AddonDirectoryPath : ScriptManager.AddonsDirsPath) {
            WatcherManager.AddPathToWatcher(AddonDirectoryPath);
        }
    }
}
