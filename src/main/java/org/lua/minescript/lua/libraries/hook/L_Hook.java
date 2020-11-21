package org.lua.minescript.lua.libraries.hook;

import org.lua.minescript.models.hook.HookModel;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class L_Hook {
    public static ArrayList<HookModel> AllHooks = new ArrayList<HookModel>();
    public static List<HookModel> UpdateHooks = AllHooks;

    public static LuaValue GetLibrary()
    {
        LuaValue library = new LuaTable();
        library.set("Add", new Add());
        library.set("Call", new Call());
        library.set("Run", new Run());
        library.set("Remove", new Remove());
        return library;
    }

    public static class Add extends VarArgFunction {
        @Override
        public Varargs onInvoke(Varargs args) {
            String n_Type = args.arg(1).checkjstring();
            String n_Name = args.arg(2).checkjstring();
            if (args.arg(3).isnumber())
                Execute(n_Type, n_Name, args.arg(3).checkint(), args.arg(4).checkfunction());
            else
                Execute(n_Type, n_Name, args.arg(3).checkfunction());

            return NIL;
        }

        public static void Execute(String Type, String Name, LuaValue HookFunction)
        {
            Execute(Type, Name, 0, HookFunction);
        }

        public static void Execute(String Type, String Name, int Index, LuaValue HookFunction)
        {
            HookModel n_Hook = new HookModel(Type, Name, Index, HookFunction);

            boolean IsHookExists = false;
            for(int i = 0; i < AllHooks.size(); i++) {
                HookModel l_Hook = AllHooks.get(i);
                if (l_Hook.Type.equals(Type) && l_Hook.Name.equals(Name)) {
                    AllHooks.set(i, n_Hook);
                    IsHookExists = true;
                    break;
                }
            }

            if (!IsHookExists) {
                AllHooks.add(n_Hook);
            }

            UpdateLists();
        }
    }

    public static class Call extends VarArgFunction {
        @Override
        public LuaValue onInvoke(Varargs Args) {
            String n_Type = Args.arg(1).checkjstring();
            String n_Name = Args.arg(2).checkjstring();
            Execute(n_Type, n_Name, Args.subargs(3));
            return NIL;
        }

        public static Boolean Execute(String Type, String Name, LuaValue[] Args)
        {
            return Execute(Type, Name, LuaValue.varargsOf(Args));
        }

        public static Boolean Execute(String Type, String Name, Varargs Args)
        {
            for (HookModel l_Hook : AllHooks) {
                if (l_Hook.Type.equals(Type) && l_Hook.Name.equals(Name)) {
                    l_Hook.Function.invoke(Args);
                    return true;
                }
            }
            return false;
        }
    }

    public static class Run extends VarArgFunction {
        @Override
        public LuaValue onInvoke(Varargs Args) {
            String n_Type = Args.arg(1).checkjstring();
            Execute(n_Type, Args.subargs(2));
            return NIL;
        }

        public static Boolean Execute(String Type, LuaValue[] Args)
        {
            return Execute(Type, LuaValue.varargsOf(Args));
        }

        public static Boolean Execute(String Type, Varargs Args)
        {
            for (HookModel l_Hook : AllHooks) {
                if (l_Hook.Type.equals(Type)) {
                    l_Hook.Function.invoke(Args);
                    return true;
                }
            }
            return false;
        }
    }

    public static class Remove extends TwoArgFunction {
        @Override
        public LuaValue call(LuaValue Type, LuaValue Name) {
            String n_Type = Type.checkjstring();
            String n_Name = Name.checkjstring();
            Execute(n_Type, n_Name);
            return NIL;
        }

        public static Boolean Execute(String Type, String Name)
        {
            for (int i = 0; i < AllHooks.size(); i++) {
                HookModel l_Hook = AllHooks.get(i);
                if (l_Hook.Type.equals(Type) && l_Hook.Name.equals(Name)) {
                    AllHooks.remove(i);
                    UpdateLists();
                    return true;
                }
            }
            return false;
        }
    }

    public static List<HookModel> GetAllHooksByType(String HookType) {
        return L_Hook.AllHooks.stream().filter(x -> x.Type.equals(HookType)).collect(Collectors.toList());
    }

    private static void UpdateLists()
    {
        UpdateHooks = AllHooks.stream()
                .filter(x -> x.Type.equals("Update"))
                .collect(Collectors.toList());

        AllHooks.sort(Comparator.comparingInt(a -> a.SortIndex));
        UpdateHooks.sort(Comparator.comparingInt(a -> a.SortIndex));
    }
}
