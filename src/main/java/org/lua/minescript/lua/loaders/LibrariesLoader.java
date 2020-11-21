package org.lua.minescript.lua.loaders;

import org.lua.minescript.LuaMachine;
import org.lua.minescript.lua.libraries.hook.L_Hook;
import org.lua.minescript.lua.libraries.time.L_CurTime;
import org.lua.minescript.lua.libraries.time.L_RealTime;
import org.lua.minescript.lua.libraries.time.L_UnixTime;
import org.lua.minescript.lua.libraries.timer.L_Timer;
import org.lua.minescript.minecraft.events.java.timer.JE_Timer;

public class LibrariesLoader {
    public static void load() {
        TimeLibraryLoader();

        LuaMachine.vm.set("hook", L_Hook.GetLibrary());

        TimerLibraryLoader();
    }

    private static void TimeLibraryLoader() {
        LuaMachine.vm.set("CurTime", new L_CurTime());
        LuaMachine.vm.set("RealTime", new L_RealTime());
        LuaMachine.vm.set("UnixTime", new L_UnixTime());

        L_CurTime.Execute();
        L_RealTime.Execute();
    }

    private static void TimerLibraryLoader() {
        LuaMachine.vm.set("timer", L_Timer.GetLibrary());
        JE_Timer.Register();
    }
}
