package org.lua.minescript.lua.entities.player;

import org.bukkit.entity.Player;
import org.lua.minescript.converter.LuaConverter;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class LuaPlayerEntity {
    private Player bukkitPlayer;
    private LuaValue luaPlayer;

    public void SetEntity(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }

    public Player GetEntity() {
        return this.bukkitPlayer;
    }

    public LuaValue GetLuaEntity() {
        if (luaPlayer == null || luaPlayer.equals(LuaValue.NIL))
            luaPlayer = GetMetatable();

        return luaPlayer;
    }

    private LuaValue GetMetatable() {
        LuaTable Meta = new LuaConverter<Player>().ConvertToLua(bukkitPlayer);
        LuaTable Library = Meta.getmetatable().checktable();
        return Meta;
    }

    private class GetBukkitEntity extends ZeroArgFunction {
        @Override
        public LuaValue call() {
            return CoerceJavaToLua.coerce(bukkitPlayer);
        }
    }
}
