package org.lua.minescript;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.lua.minescript.managers.directory.DirectoryManager;

public class Main extends JavaPlugin {
    public static final String pluginName = "MineScript";
    public static final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
    public static Plugin plugin;

    @Override
    public void onEnable() {
        console.sendMessage(ChatColor.AQUA + "Loading the \""+ pluginName + "\" plugin");
        plugin = this;

        DirectoryManager.SetPluginPath(this.getDataFolder().getPath());
        DirectoryManager.CreatePluginDirectory();

        console.sendMessage(ChatColor.AQUA + "Plugin \""+ pluginName + "\" loaded");
    }
}
