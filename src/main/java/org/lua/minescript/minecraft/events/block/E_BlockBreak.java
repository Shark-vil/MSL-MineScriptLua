package org.lua.minescript.minecraft.events.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.lua.minescript.converter.LuaConverter;
import org.lua.minescript.lua.libraries.hook.L_Hook;
import org.lua.minescript.models.hook.HookModel;
import org.lua.minescript.storage.player.PlayersStorage;
import org.luaj.vm2.LuaValue;

import java.util.List;

public class E_BlockBreak implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void OnBlockBreak(BlockBreakEvent e) {
        List<HookModel> Hooks = L_Hook.GetAllHooksByType("BlockBreak");

        LuaValue l_Player = PlayersStorage.Get(e.getPlayer()).GetLuaEntity();
        LuaValue l_Block = new LuaConverter<Block>().ConvertToLua(e.getBlock());

        for(HookModel HookItem : Hooks) {
            LuaValue Result = HookItem.Function.call(l_Player, l_Block);
            if (Result.isboolean() && !Result.checkboolean()) {
                e.setCancelled(true);
                return;
            }
        }
    }
}
