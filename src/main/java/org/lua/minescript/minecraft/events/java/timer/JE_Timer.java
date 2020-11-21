package org.lua.minescript.minecraft.events.java.timer;

import org.bukkit.ChatColor;
import org.lua.minescript.Main;
import org.lua.minescript.lua.libraries.time.L_CurTime;
import org.lua.minescript.lua.libraries.timer.L_Timer;
import org.lua.minescript.models.timer.TimerModel;

public class JE_Timer {
    public static void Register()
    {
        try {
            Main.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
                public void run() {
                    TimerModel l_Timer = null;
                    try {
                        for (int i = 0; i < L_Timer.Timers.size(); i++) {
                            l_Timer = L_Timer.Timers.get(i);
                            if (l_Timer.CurrentDelay < L_CurTime.Execute()) {
                                l_Timer.Function.call();
                                if (l_Timer.Repetitions != 0) {
                                    l_Timer.AddRepeat();
                                    if (l_Timer.CurrentRepeat >= l_Timer.Repetitions) {
                                        L_Timer.Remove.Execute(l_Timer.Name);
                                        continue;
                                    }
                                }
                                l_Timer.AddDelay();
                            }
                        }
                    }
                    catch (Throwable ex) {
                        Main.console.sendMessage(ChatColor.RED + "Error processing timer \"" +
                                ((l_Timer == null) ? "none" : l_Timer.Name) + "\":\n" + ex);

                        if (l_Timer != null)
                            L_Timer.Remove.Execute(l_Timer.Name);
                    }

                }}, 0, 1);
        }
        catch (Throwable ex) {
            Main.console.sendMessage(ChatColor.RED + "Something is creating script error:\n" + ex);
        }
    }
}
