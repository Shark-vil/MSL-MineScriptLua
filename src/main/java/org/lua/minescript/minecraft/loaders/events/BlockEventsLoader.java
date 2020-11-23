package org.lua.minescript.minecraft.loaders.events;

import org.lua.minescript.Main;
import org.lua.minescript.minecraft.events.block.*;

public class BlockEventsLoader {
    public static void loader() {
        Main.pluginManager.registerEvents(new E_BlockBreak(), Main.plugin);
        Main.pluginManager.registerEvents(new E_BlockBurn(), Main.plugin);
        Main.pluginManager.registerEvents(new E_BlockCanBuild(), Main.plugin);
        Main.pluginManager.registerEvents(new E_BlockDamage(), Main.plugin);
        Main.pluginManager.registerEvents(new E_BlockPlace(), Main.plugin);
    }
}
