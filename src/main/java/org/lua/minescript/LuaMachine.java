package org.lua.minescript;

import org.bukkit.plugin.java.JavaPlugin;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaMachine {
    public static final Globals vm = JsePlatform.standardGlobals();

    public LuaMachine(JavaPlugin plugin) {

    }
}
