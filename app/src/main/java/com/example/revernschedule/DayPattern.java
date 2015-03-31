package com.example.revernschedule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Алмаз on 16.03.2015.
 */
public class DayPattern {
    private ArrayList<Action> fullDay;

    public String getName() {
        return name;
    }

    private String name;
    public DayPattern(String name){
        this.name=name;
        fullDay=new ArrayList<Action>();
    }
    public void remove(int index){
        fullDay.remove(index);
    }
    public void addAction(Action action){
        fullDay.add(action);
        Collections.sort(fullDay, new Comparator<Action>() {
            @Override
            public int compare(Action a1, Action a2) {
                return a1.getStartHour() * 60 + a1.getStartMinute() - a2.getStartHour() * 60 + a2.getStartMinute();
            }
        });
    }
    public Action get(int index){
        return fullDay.get(index);
    }
    public int size(){
        return fullDay.size();
    }
}
