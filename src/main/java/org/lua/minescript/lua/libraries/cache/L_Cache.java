package org.lua.minescript.lua.libraries.cache;

import org.lua.minescript.storage.cache.CacheStorage;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class L_Cache extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        return GetLibrary();
    }

    public static LuaValue GetLibrary() {
        LuaValue library = new LuaTable();
        library.set("Save", new Save());
        library.set("Remove", new Remove());
        library.set("IsExist", new IsExist());
        return library;
    }

    public static class Save extends VarArgFunction {
        @Override
        public Varargs onInvoke(Varargs args) {
            LuaTable LuaValues = new LuaTable(args);
            for (int i = 0; i < LuaValues.length(); i++) {
                LuaValue LuaItem = LuaValues.get(i + 1);
                CacheStorage.Add(LuaItem);
            }

            return NIL;
        }
    }

    public static class Remove extends VarArgFunction {
        @Override
        public Varargs onInvoke(Varargs args) {
            LuaTable LuaValues = new LuaTable(args);
            for (int i = 0; i < LuaValues.length(); i++) {
                LuaValue LuaItem = LuaValues.get(i + 1);
                CacheStorage.Remove(LuaItem);
            }

            return NIL;
        }
    }

    public static class IsExist extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue LuaItem) {
            if (CacheStorage.IsExist(LuaItem))
                return LuaValue.valueOf(true);

            return LuaValue.valueOf(false);
        }
    }
}
