package org.lua.minescript.storage.cache;
import org.lua.minescript.converter.LuaConverter;
import org.luaj.vm2.LuaValue;
import java.util.ArrayList;
import java.util.Iterator;

public class CacheStorage<T> {
    private static final ArrayList<LuaValue> Cache = new ArrayList<LuaValue>();

    public static void Add(LuaValue LuaItem) {
        if (!IsExist(LuaItem))
            Cache.add(LuaItem);
    }

    public static void Remove(LuaValue LuaItem) {
        Cache.removeIf(x -> x.equals(LuaItem));
    }

    public static boolean IsExist(LuaValue LuaItem) {
        return Cache.stream().anyMatch(x -> x.equals(LuaItem));
    }

    public static void Clear() {
        Iterator<LuaValue> iter = Cache.iterator();
        while (iter.hasNext()){
            LuaValue CacheItem = iter.next();
            if (CacheItem.isnil()) {
                iter.remove();
            } else {
                LuaValue LuaBukkit = CacheItem.get("bukkit");
                if (LuaBukkit.isnil()) {
                    iter.remove();
                }
            }
        }
    }

    public static LuaValue GetById(int Id) {
        for (LuaValue LuaItem : Cache) {
            LuaValue LuaId = LuaItem.get("id");
            if (!LuaId.isnil() && LuaId.checkint() == Id)
                return LuaItem;
        }
        return LuaValue.NIL;
    }

    public LuaValue GetLuaObjectFromJava(T javaObject) {
        for (LuaValue LuaItem : Cache) {
            LuaValue LuaBukkit = LuaItem.get("bukkit");
            try {
                T BukkitObject = new LuaConverter<T>().ConvertToBukkit((Class<T>) javaObject.getClass(), LuaBukkit);
                if (BukkitObject != null && BukkitObject.equals(javaObject)) {
                    return LuaItem;
                }
            }
            catch (Exception ex) { }
        }
        return LuaValue.NIL;
    }
}
