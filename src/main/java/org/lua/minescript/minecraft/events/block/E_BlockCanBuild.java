package org.lua.minescript.minecraft.events.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.lua.minescript.converter.LuaConverter;
import org.lua.minescript.lua.libraries.hook.L_Hook;
import org.lua.minescript.models.hook.HookModel;
import org.lua.minescript.storage.player.PlayersStorage;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.List;

public class E_BlockCanBuild implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void OnBlockCanBuild(BlockCanBuildEvent e) {
        List<HookModel> Hooks = L_Hook.GetAllHooksByType("BlockCanBuild");

        LuaValue l_Player = PlayersStorage.Get(e.getPlayer()).GetLuaEntity();
        LuaValue l_Block = new LuaConverter<Block>().ConvertToLua(e.getBlock());
        LuaValue l_Material = new LuaConverter<Material>().ConvertToLua(e.getMaterial());
        LuaValue l_BlockData = new LuaConverter<BlockData>().ConvertToLua(e.getBlockData());

        LuaValue[] Args = {
            l_Player, l_Block, l_Material, l_BlockData
        };

        for(HookModel HookItem : Hooks) {
            LuaValue Result = (LuaValue) HookItem.Function.invoke(Args);
            if (Result.isboolean()) {
                e.setBuildable(Result.checkboolean());
                return;
            }
        }
    }
}
