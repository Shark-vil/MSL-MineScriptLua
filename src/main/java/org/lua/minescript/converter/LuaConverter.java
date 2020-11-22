package org.lua.minescript.converter;

import org.lua.minescript.storage.cache.CacheStorage;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LuaConverter<T> {
    public LuaValue ConvertToLua(T javaObject) {
        int IdEntity = javaObject.hashCode();

        //LuaValue CacheItem = new CacheStorage<T>().GetLuaObjectFromJava(javaObject);
        LuaValue CacheItem = CacheStorage.GetById(IdEntity);
        if (!CacheItem.isnil())
            return CacheItem;

        LuaTable Meta = new LuaTable();
        LuaTable Library = new LuaTable();

        GetBukkitEntity<T> bukkitEntity = new GetBukkitEntity<T>(javaObject);
        Library.set("id", IdEntity);
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
