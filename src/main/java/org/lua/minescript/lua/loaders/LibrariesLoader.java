package org.lua.minescript.lua.loaders;

import org.lua.minescript.LuaMachine;
import org.lua.minescript.lua.libraries.hook.L_Hook;

public class LibrariesLoader {
    public static void load() {
        LuaMachine.vm.set("hook", L_Hook.GetLibrary());
    }
}
