package org.lua.minescript;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.lua.minescript.managers.directory.DirectoryManager;
import org.lua.minescript.minecraft.loaders.MainMinecraftLoader;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin implements Listener {
    public static final String pluginName = "MineScript";
    public static final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
    public static Plugin plugin;
    public static PluginManager pluginManager;

    @Override
    public void onEnable() {
        console.sendMessage(ChatColor.AQUA + "Loading the \""+ pluginName + "\" plugin");
        plugin = this;
        pluginManager = this.getServer().getPluginManager();
        /*
        pluginManager.registerEvents(this, this);
        getConfig().set("minescript.demo", "Test command");
        try {
            getConfig().save(this.getDataFolder().getPath() + "\\cfg.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        DirectoryManager.SetPluginPath(this.getDataFolder().getPath());
        DirectoryManager.CreatePluginDirectory();

        MainMinecraftLoader.loader();
        LuaMachine L_State = new LuaMachine();

        console.sendMessage(ChatColor.AQUA + "Plugin \""+ pluginName + "\" loaded");
    }
}
