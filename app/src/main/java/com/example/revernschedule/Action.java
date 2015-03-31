package com.example.revernschedule;

import android.text.format.Time;

/**
 * Created by Алмаз on 16.03.2015.
 */
public class Action {
    public int getStartHour() {
        return StartHour;
    }

    public void setStartHour(int startHour) {
        StartHour = startHour;
    }

    public int getStartMinute() {
        return StartMinute;
    }

    public void setStartMinute(int startMinute) {
        StartMinute = startMinute;
    }

    public int getFinishHour() {
        return FinishHour;
    }

    public void setFinishHour(int finishHour) {
        FinishHour = finishHour;
    }

    public int getFinishMinute() {
        return FinishMinute;
    }

    public void setFinishMinute(int finishMinute) {
        FinishMinute = finishMinute;
    }

    public String getMainAction() {
        return mainAction;
    }

    public void setMainAction(String mainAction) {
        this.mainAction = mainAction;
    }

    public String getSubAction() {
        return subAction;
    }

    public void setSubAction(String subAction) {
        this.subAction = subAction;
    }

    private int StartHour;
    private int StartMinute;
    private int FinishHour;
    private int FinishMinute;
    private String mainAction;
    private String subAction="";
    public Action(){

    }
}
