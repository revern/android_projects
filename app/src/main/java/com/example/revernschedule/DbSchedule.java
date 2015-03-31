package com.example.revernschedule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Алмаз on 17.03.2015.
 */
public class DbSchedule extends SQLiteOpenHelper {
    public DbSchedule(Context context) {
        super(context, "DbSchedule", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table patterns (id integer primary key autoincrement, pattern text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
