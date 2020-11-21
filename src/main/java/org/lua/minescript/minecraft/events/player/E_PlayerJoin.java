package org.lua.minescript.minecraft.events.player;

import com.mojang.datafixers.types.templates.Hook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.lua.minescript.lua.entities.player.LuaPlayerEntity;
import org.lua.minescript.lua.libraries.hook.L_Hook;
import org.lua.minescript.models.hook.HookModel;
import org.lua.minescript.storage.player.PlayersStorage;
import org.luaj.vm2.LuaValue;

import java.util.List;

public class E_PlayerJoin implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void OnPlayerJoin(PlayerJoinEvent e) {
        List<HookModel> Hooks = L_Hook.GetAllHooksByType("PlayerJoin");
        LuaPlayerEntity luaPlayerEntity = PlayersStorage.Add(e.getPlayer());
        LuaValue luaPlayer = luaPlayerEntity.GetLuaEntity();

        for (HookModel HookItem : Hooks) {
            LuaValue Result = HookItem.Function.call(luaPlayer);
            if (Result.isstring())
                e.setJoinMessage(Result.checkjstring());
        }
    }
}
