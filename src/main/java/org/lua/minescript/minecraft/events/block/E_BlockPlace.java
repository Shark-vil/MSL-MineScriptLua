package org.lua.minescript.minecraft.events.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.lua.minescript.converter.LuaConverter;
import org.lua.minescript.lua.libraries.hook.L_Hook;
import org.lua.minescript.models.hook.HookModel;
import org.lua.minescript.storage.player.PlayersStorage;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.List;

public class E_BlockPlace implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void OnBlockPlace(BlockPlaceEvent e) {
        List<HookModel> Hooks = L_Hook.GetAllHooksByType("BlockPlace");

        LuaValue Player = PlayersStorage.Get(e.getPlayer()).GetLuaEntity();
        LuaValue Block = new LuaConverter<Block>().ConvertToLua(e.getBlock());
        LuaValue BlockReplacedState = new LuaConverter<BlockState>().ConvertToLua(e.getBlockReplacedState());
        LuaValue BlockAgainst = new LuaConverter<Block>().ConvertToLua(e.getBlockAgainst());
        LuaValue ItemInHand = new LuaConverter<ItemStack>().ConvertToLua(e.getItemInHand());
        LuaValue CanBuild = CoerceJavaToLua.coerce(e.canBuild());

        LuaValue[] Args = {
                Player, Block, BlockReplacedState, BlockAgainst, ItemInHand, CanBuild
        };

        for(HookModel HookItem : Hooks) {
            LuaValue Result = (LuaValue) HookItem.Function.invoke(LuaValue.varargsOf(Args));
            if (Result.isboolean() && !Result.checkboolean()) {
                e.setCancelled(true);
                break;
            }
        }
    }
}
