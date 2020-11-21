package org.lua.minescript.lua.libraries.time;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class L_RealTime extends ZeroArgFunction {
    public static long RealTimeFromStart = -1;
    public static long LastRealTime = 0;
    public static long RealTime = 0;

    public L_RealTime() {}

    @Override
    public LuaValue call() {
        return LuaValue.valueOf(Execute());
    }

    public static long Execute() {
        if (RealTimeFromStart == -1)
            RealTimeFromStart = System.currentTimeMillis();

        LastRealTime = System.currentTimeMillis();
        RealTime = LastRealTime - RealTimeFromStart;
        return RealTime;
    }
}
