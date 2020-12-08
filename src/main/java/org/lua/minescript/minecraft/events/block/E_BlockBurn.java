package org.lua.minescript.minecraft.events.block;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.lua.minescript.converter.LuaConverter;
import org.lua.minescript.lua.libraries.hook.L_Hook;
import org.lua.minescript.models.hook.HookModel;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.List;

public class E_BlockBurn implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void OnBlockBurn(BlockBurnEvent e) {
        List<HookModel> Hooks = L_Hook.GetAllHooksByType("BlockBurn");

        LuaValue l_Event = CoerceJavaToLua.coerce(e);
        LuaValue l_Block = new LuaConverter<Block>().ConvertToLua(e.getBlock());
        LuaValue l_IgnitingBlock = new LuaConverter<Block>().ConvertToLua(e.getIgnitingBlock());

        for(HookModel HookItem : Hooks) {
            LuaValue Result;

            if (HookItem.IsEvent)
                Result = HookItem.Function.call(l_Event);
            else {
                Result = HookItem.Function.call(l_Block, l_IgnitingBlock);

                if (Result.isboolean())
                    e.setCancelled(!Result.checkboolean());
            }

            if (!Result.isnil())
                return;;
        }
    }
}
