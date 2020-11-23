package org.lua.minescript.models.command;

import org.bukkit.command.Command;
import org.lua.minescript.minecraft.commands.GeneralLuaCmd;
import org.luaj.vm2.LuaValue;

public class CommandModel {
    public String CommandName;
    public String Description;
    public LuaValue Function;
    public String Permission;
    public LuaValue AutoComplete;
    public Command JavaCommand;
}
