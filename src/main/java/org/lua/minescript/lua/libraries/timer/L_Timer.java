package org.lua.minescript.lua.libraries.timer;

import org.lua.minescript.models.timer.TimerModel;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.util.ArrayList;

public class L_Timer extends ZeroArgFunction {
    public static ArrayList<TimerModel> Timers = new ArrayList<TimerModel>();

    @Override
    public LuaValue call() {
        return GetLibrary();
    }

    public static LuaValue GetLibrary() {
        LuaValue library = new LuaTable();
        library.set("Create", new Create());
        library.set("Remove", new Remove());
        return library;
    }

    public static class Create extends VarArgFunction {
        @Override
        public Varargs onInvoke(Varargs args) {
            String Name = args.arg(1).checkjstring();
            double Delay = args.arg(2).checkdouble();
            int Repetitions = args.arg(3).checkint();
            LuaValue Function = args.arg(4);

            Execute(Name, Delay, Repetitions, Function);

            return NIL;
        }

        public static void Execute(String Name, double Delay, int Repetitions, LuaValue Function) {
            TimerModel n_Timer = new TimerModel(Name, Delay, Repetitions, Function);

            boolean IsTimerExists = false;
            for(int i = 0; i < Timers.size(); i++) {
                TimerModel l_Timer = Timers.get(i);
                if (l_Timer.Name.equals(Name)) {
                    Timers.set(i, n_Timer);
                    IsTimerExists = true;
                    break;
                }
            }

            if (!IsTimerExists) {
                Timers.add(n_Timer);
            }
        }
    }

    public static class Remove extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue l_TimerName) {
            Execute(l_TimerName.checkjstring());
            return NIL;
        }

        public static void Execute(String TimerName) {
            Timers.removeIf(x -> x.Name.equals(TimerName));
        }
    }
}
