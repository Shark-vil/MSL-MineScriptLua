package org.lua.minescript.lua.loaders;

import org.lua.minescript.LuaMachine;
import org.lua.minescript.lua.libraries.hook.L_Hook;
import org.lua.minescript.lua.libraries.time.L_CurTime;
import org.lua.minescript.lua.libraries.time.L_RealTime;
import org.lua.minescript.lua.libraries.time.L_UnixTime;

public class LibrariesLoader {
    public static void load() {
        TimeLibraryLoader();
        LuaMachine.vm.set("hook", L_Hook.GetLibrary());
    }

    private static void TimeLibraryLoader() {
        LuaMachine.vm.set("CurTime", new L_CurTime());
        LuaMachine.vm.set("RealTime", new L_RealTime());
        LuaMachine.vm.set("UnixTime", new L_UnixTime());
    }
}
