package org.lua.minescript.minecraft.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.lua.minescript.lua.entities.player.LuaPlayerEntity;
import org.lua.minescript.lua.libraries.hook.L_Hook;
import org.lua.minescript.models.hook.HookModel;
import org.lua.minescript.storage.player.PlayersStorage;
import org.luaj.vm2.LuaValue;

import java.util.List;

public class E_PlayerQuit implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void OnPlayerQuit(PlayerQuitEvent e) {
        List<HookModel> Hooks = L_Hook.GetAllHooksByType("PlayerQuit");
        LuaPlayerEntity luaPlayerEntity = PlayersStorage.Get(e.getPlayer());
        LuaValue luaPlayer = luaPlayerEntity.GetLuaEntity();

        for (HookModel HookItem : Hooks) {
            LuaValue Result = HookItem.Function.call(luaPlayer);
            if (Result.isstring())
                e.setQuitMessage(Result.checkjstring());
        }

        PlayersStorage.Remove(e.getPlayer());
    }
}
