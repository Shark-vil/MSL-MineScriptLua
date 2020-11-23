package org.lua.minescript.lua.libraries.command;

import org.lua.minescript.models.command.CommandModel;
import org.lua.minescript.storage.command.CommandsStorage;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class L_Command extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        return GetLibrary();
    }

    public static LuaValue GetLibrary() {
        LuaValue library = new LuaTable();
        library.set("Add", new Add());
        library.set("Remove", new Remove());
        return library;
    }

    public static class Add extends VarArgFunction {
        @Override
        public Varargs onInvoke(Varargs args) {
            String CommandName = args.arg(1).checkjstring();
            LuaValue l_Function = args.arg(2);
            String l_Permission = (args.arg(3).isstring()) ? args.arg(3).checkjstring() : "";
            LuaValue l_AutoComplete = args.arg(4);
            String Description = (args.arg(5).isstring()) ? args.arg(5).checkjstring() : "";

            CommandModel cmd = new CommandModel();
            cmd.CommandName = CommandName;
            cmd.Description = Description;
            cmd.Function = l_Function;
            cmd.AutoComplete = l_AutoComplete;
            cmd.Permission = l_Permission;

            CommandsStorage.Add(cmd);

            return LuaValue.NIL;
        }
    }

    public static class Remove extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue l_CommandName) {
            CommandsStorage.Remove(l_CommandName.checkjstring());
            return LuaValue.NIL;
        }
    }
}
