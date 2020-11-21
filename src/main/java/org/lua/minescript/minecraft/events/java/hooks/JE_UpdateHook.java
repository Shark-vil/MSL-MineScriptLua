package org.lua.minescript.minecraft.events.java.hooks;

import org.bukkit.ChatColor;
import org.lua.minescript.Main;
import org.lua.minescript.lua.libraries.hook.L_Hook;
import org.lua.minescript.models.hook.HookModel;

import java.util.ArrayList;
import java.util.List;

public class JE_UpdateHook {
    public static void Register()
    {
        try {
            Main.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
                public void run() {
                    List<HookModel> CloneUpdateFunctions =
                            new ArrayList<HookModel>(L_Hook.UpdateHooks);

                    for (HookModel l_Hook : CloneUpdateFunctions)
                        l_Hook.Function.call();

                }}, 0, 1);
        }
        catch (Throwable ex) {
            Main.console.sendMessage(ChatColor.RED + "Something is creating script error:\n" + ex);
        }
    }
}
