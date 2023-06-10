package com.example.myapplication.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "sports_app";
    private final static int DB_SCHEMA = 1;

    private final static String CREATE_EVENTS_TABLE = "create table if not exists events(" +
            "id integer primary key autoincrement," +
            "event text," +
            "uid text," +
            "date text," +
            "month text," +
            "year text)";
    private static final String DROP_EVENTS_TABLE = "drop table if exists events";

    public DBOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_EVENTS_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void saveEvent(String event, String uid, String date, String month, String year, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("event", event);
        contentValues.put("uid", uid);
        contentValues.put("date", date);
        contentValues.put("month", month);
        contentValues.put("year", year);

        db.insert("events", null, contentValues);
    }

    public Cursor readEvents(String date, SQLiteDatabase db) {
        String[] projections = {"event", "uid", "date", "month", "year"};
        String selection = "date=?";
        String[] selectionArgs = {date};
        return db.query("events", projections, selection, selectionArgs, null, null, null);
    }

    public Cursor readEventPerMonth(String month, String year, SQLiteDatabase db) {
        String[] projections = {"event", "uid", "date", "month", "year"};
        String selection = "month=? and year=?";
        String[] selectionArgs = {month, year};
        return db.query("events", projections, selection, selectionArgs, null, null, null);
    }
}
