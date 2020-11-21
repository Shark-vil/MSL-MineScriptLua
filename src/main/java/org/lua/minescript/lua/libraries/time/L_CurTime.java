package org.lua.minescript.lua.libraries.time;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class L_CurTime extends ZeroArgFunction {
    public static long CurTimeFromStart = -1;
    public static long LastCurTime = 0;
    public static long CurTime = 0;

    public L_CurTime() {}

    @Override
    public LuaValue call() {
        return LuaValue.valueOf(Execute());
    }

    public static long Execute() {
        if (CurTimeFromStart == -1)
            CurTimeFromStart = System.currentTimeMillis();

        LastCurTime = System.currentTimeMillis();
        CurTime = (LastCurTime - CurTimeFromStart) / 1000;
        return CurTime;
    }
}
