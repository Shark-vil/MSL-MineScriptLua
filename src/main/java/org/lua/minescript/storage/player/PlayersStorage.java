package org.lua.minescript.storage.player;

import org.bukkit.entity.Player;
import org.lua.minescript.lua.entities.player.LuaPlayerEntity;

import java.util.ArrayList;

public class PlayersStorage {
    private static ArrayList<LuaPlayerEntity> Players = new ArrayList<LuaPlayerEntity>();

    public static LuaPlayerEntity Add(Player bukkitPlayer) {
        boolean isExist = IsExist(bukkitPlayer);
        if (!isExist) {
            LuaPlayerEntity luaPlayer = new LuaPlayerEntity();
            luaPlayer.SetEntity(bukkitPlayer);
            Players.add(luaPlayer);
            return luaPlayer;
        }
        return Get(bukkitPlayer);
    }

    public static void Remove(Player bukkitPlayer) {
        Players.removeIf(x -> x.GetEntity().equals(bukkitPlayer));
    }

    public static LuaPlayerEntity Get(Player bukkitPlayer) {
        return Players.stream().filter(x ->x.GetEntity().equals(bukkitPlayer)).findFirst().get();
    }

    public static boolean IsExist(Player bukkitPlayer) {
        if (Players.stream().noneMatch(x -> x.GetEntity().equals(bukkitPlayer)))
            return false;
        return true;
    }
}
