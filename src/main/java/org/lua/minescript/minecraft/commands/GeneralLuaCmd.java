package org.lua.minescript.minecraft.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.lua.minescript.converter.LuaConverter;
import org.lua.minescript.models.command.CommandModel;
import org.lua.minescript.storage.command.CommandsStorage;
import org.lua.minescript.storage.player.PlayersStorage;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.List;

public class GeneralLuaCmd extends BukkitCommand {
    public GeneralLuaCmd(CommandModel cmd) {
        super(cmd.CommandName);
        this.description = cmd.Description;
        this.setPermission(cmd.Permission);
        this.setAliases(new ArrayList<String>());
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) throws IllegalArgumentException {
        CommandModel cmd = CommandsStorage.GetCommand(getName());
        if (cmd != null) {
            LuaValue l_Sender = GetSender(sender);
            LuaValue l_Alias = LuaValue.valueOf(alias);
            LuaTable l_CmdArgs = GetArgs(args);
            LuaValue l_Location = new LuaConverter<Location>().ConvertToLua(location);

            LuaValue[] l_Args = {
                l_Sender, l_Alias, l_CmdArgs, l_Location
            };

            LuaTable l_SortTable = (LuaTable) cmd.AutoComplete.invoke(l_Args);
            if (l_SortTable.istable()) {
                ArrayList<String> CompleteResult = new ArrayList<String>();

                for (int i = 0; i < l_SortTable.length(); i++) {
                    LuaValue l_SortItem = l_SortTable.get(i + 1);
                    if (l_SortItem.isstring()) {
                        CompleteResult.add(l_SortItem.checkjstring());
                    }
                }

                return CompleteResult;
            }
        }

        return super.tabComplete(sender, alias, args, location);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        CommandModel cmd = CommandsStorage.GetCommand(getName());
        if (cmd != null) {
            LuaValue l_Sender = GetSender(sender);

            LuaValue l_Command = LuaValue.valueOf(cmd.CommandName);
            LuaValue l_Description = LuaValue.valueOf(cmd.Description);
            LuaValue l_CmdLabel = LuaValue.valueOf(commandLabel);
            LuaTable l_CmdArgs = GetArgs(args);

            LuaValue[] l_Args = {
                l_Sender, l_Command, l_CmdArgs, l_CmdLabel, l_Description
            };

            LuaValue Result = (LuaValue) cmd.Function.invoke(l_Args);
            return (Result.isboolean()) ? Result.checkboolean() : true;
        }

        return true;
    }

    private LuaValue GetSender(CommandSender sender) {
        LuaValue l_Sender;

        if (sender instanceof Player)
            l_Sender = PlayersStorage.Get((Player)sender).GetLuaEntity();
        else
            l_Sender = new LuaConverter<CommandSender>().ConvertToLua(sender);

        return l_Sender;
    }

    private LuaTable GetArgs(String[] args) {
        LuaTable l_CmdArgs = new LuaTable();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            l_CmdArgs.set(i + 1, LuaValue.valueOf(arg));
        }

        return l_CmdArgs;
    }
}
