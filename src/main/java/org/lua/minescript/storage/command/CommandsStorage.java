package org.lua.minescript.storage.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.lua.minescript.Main;
import org.lua.minescript.minecraft.commands.GeneralLuaCmd;
import org.lua.minescript.models.command.CommandModel;

import java.util.ArrayList;

public class CommandsStorage {
    public static final ArrayList<CommandModel> Commands = new ArrayList<CommandModel>();

    public static void Add(CommandModel cmd) {
        if (Commands.stream().noneMatch(x -> x.CommandName.equals(cmd.CommandName))) {
            Commands.add(cmd);
        } else {
            for (int i = 0; i < Commands.size(); i++) {
                if (Commands.get(i).CommandName.equals(cmd.CommandName)) {
                    Commands.set(i, cmd);
                    break;
                }
            }
        }

        RegisterJavaCommand(cmd);
    }

    public static void Remove(String CommandName) {
        SimpleCommandMap CommandMap = ((CraftServer) Main.plugin.getServer()).getCommandMap();
        CommandModel CmdItem = GetCommand(CommandName);

        if (CmdItem != null) {
            //CmdItem.JavaCommand.unregister(CommandMap);
            /*
            Command GettingCommand = CommandMap.getCommand(CmdItem.CommandName);
            if (GettingCommand != null)
                GettingCommand.unregister(CommandMap);
            */
            Commands.remove(CmdItem);
        }
    }

    private static void RegisterJavaCommand(CommandModel cmd) {
        SimpleCommandMap CommandMap = ((CraftServer) Main.plugin.getServer()).getCommandMap();
        CommandModel CmdItem = GetCommand(cmd.CommandName);

        if (CmdItem != null) {
            CmdItem.JavaCommand = new GeneralLuaCmd(cmd);
            CommandMap.register(cmd.CommandName, CmdItem.JavaCommand);
        }
    }

    public static CommandModel GetCommand(String CommandName) {
        return Commands.stream()
                .filter(x -> x.CommandName.equals(CommandName))
                .findAny()
                .orElse(null);
    }

    public static boolean IsExist(CommandModel cmd) {
        return Commands.stream().anyMatch(x -> x.equals(cmd));
    }
}
