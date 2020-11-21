package org.lua.minescript.converter;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import java.lang.reflect.Type;

public class LuaConverter<T> {
    public LuaTable ConvertToLua(T javaObject) {
        LuaTable Meta = new LuaTable();
        LuaTable Library = new LuaTable();

        GetBukkitEntity<T> bukkitEntity = new GetBukkitEntity<T>(javaObject);
        Library.set("id", javaObject.hashCode());
        Library.set("bukkit", bukkitEntity);
        Library.set("library", Library);

        Library.set(LuaValue.INDEX, Library);
        Library.set(LuaValue.CALL, bukkitEntity);

        Meta.setmetatable(Library);
        return Meta;
    }

    public T ConvertToBukkit(Class<T> tClass, LuaValue luaObject) {
        try {
            T javaObject = (T) CoerceLuaToJava.coerce(luaObject, tClass);
            return javaObject;
        } catch (Exception ex) {
            return null;
        }
    }

    private static class GetBukkitEntity<T> extends ZeroArgFunction {
        private final T bukkitObject;

        public GetBukkitEntity(T bukkitObject) {
            this.bukkitObject = bukkitObject;
        }

        @Override
        public LuaValue call() {
            return CoerceJavaToLua.coerce(bukkitObject);
        }
    }
}
