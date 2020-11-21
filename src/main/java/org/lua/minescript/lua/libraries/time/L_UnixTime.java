package org.lua.minescript.lua.libraries.time;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class L_UnixTime extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        return LuaValue.valueOf(System.currentTimeMillis());
    }
}
