package org.lua.minescript.models.hook;

import org.luaj.vm2.LuaValue;

public class HookModel {
    public HookModel(String Type, String Name, int SortIndex, LuaValue Function) {
        this.Type = Type;
        this.Name = Name;
        this.SortIndex = SortIndex;
        this.Function = Function;
    }

    public int SortIndex = 0;
    public String Type;
    public String Name;
    public LuaValue Function;
}