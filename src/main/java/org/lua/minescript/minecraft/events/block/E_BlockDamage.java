package org.lua.minescript.minecraft.events.block;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.lua.minescript.converter.LuaConverter;
import org.lua.minescript.lua.libraries.hook.L_Hook;
import org.lua.minescript.models.hook.HookModel;
import org.lua.minescript.storage.player.PlayersStorage;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.List;

public class E_BlockDamage implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void OnBlockDamage(BlockDamageEvent e) {
        List<HookModel> Hooks = L_Hook.GetAllHooksByType("BlockDamage");

        LuaValue l_Event = CoerceJavaToLua.coerce(e);
        LuaValue l_Player = PlayersStorage.Get(e.getPlayer()).GetLuaEntity();
        LuaValue l_Block = new LuaConverter<Block>().ConvertToLua(e.getBlock());
        LuaValue l_ItemInHand = new LuaConverter<ItemStack>().ConvertToLua(e.getItemInHand());
        LuaValue l_InstaBreak = LuaValue.valueOf(e.getInstaBreak());

        LuaValue[] Args = {
            l_Player, l_Block, l_ItemInHand, l_InstaBreak
        };

        for(HookModel HookItem : Hooks) {
            Varargs ArgsResult;

            if (HookItem.IsEvent)
                ArgsResult = HookItem.Function.call(l_Event);
            else {
                ArgsResult = HookItem.Function.invoke(Args);

                LuaValue a_Cancelled = ArgsResult.arg(1);
                LuaValue a_InstaBreak = ArgsResult.arg(2);

                if (a_InstaBreak.isboolean())
                    e.setInstaBreak(a_InstaBreak.checkboolean());

                if (a_Cancelled.isboolean())
                    e.setCancelled(a_Cancelled.checkboolean());
            }

            if (!ArgsResult.isnil(1))
                return;;
        }
    }
}
