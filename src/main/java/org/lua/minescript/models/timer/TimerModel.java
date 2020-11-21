package org.lua.minescript.models.timer;

import org.lua.minescript.lua.libraries.time.L_CurTime;
import org.luaj.vm2.LuaValue;

public class TimerModel {
    public TimerModel(String Name, double Delay, int Repetitions, LuaValue Function) {
        this.Name = Name;
        this.Delay = Delay;
        this.Repetitions = Repetitions;
        this.Function = Function;
        AddDelay();
    }

    public void AddDelay() {
        this.CurrentDelay = L_CurTime.Execute() + this.Delay;
    }

    public void AddRepeat() {
        CurrentRepeat++;
    }

    public String Name;
    public double Delay;
    public int Repetitions;
    public LuaValue Function;
    public double CurrentDelay;
    public int CurrentRepeat = 0;
}
