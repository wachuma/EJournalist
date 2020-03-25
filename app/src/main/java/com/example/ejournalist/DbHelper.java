package com.example.ejournalist;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "journalist.db";
    public static final String TABLE_EVENTS = "events_table";
    public static final String TABLE_NOTES = "notes_table";
    public static final String COL_EVENTS_ID="EVENTS_ID";
    public static final String COL_NOTES_ID="NOTES_ID";
    public static final String COL_NAME="NAME";
    public static final String COL_NOTE="NOTE";
    public static final String COL_CREATED="CREATED";

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table "+TABLE_EVENTS+" (EVENTS_ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)");
       db.execSQL("create table "+TABLE_NOTES+" (NOTES_ID INTEGER PRIMARY KEY AUTOINCREMENT, NOTE TEXT, CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY ("+COL_EVENTS_ID+") REFERENCES "+TABLE_EVENTS+"("+COL_EVENTS_ID+"))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENTS);
        onCreate(db);
    }
    public boolean insertEventsData(String name, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        long result =  db.insert(TABLE_EVENTS, null, contentValues);
        if(result == -1){
            return false;
        }
        else
            return true;

    }
    public boolean insertNotesData(String note, String eventId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOTE, note);
        contentValues.put(COL_EVENTS_ID, eventId);
        long result =  db.insert(TABLE_NOTES, null, contentValues);
        if(result == -1){
            return false;
        }
        else
            return true;

    }
    public Cursor getAllEventsData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_EVENTS,null);
        return res;
    }

    public Cursor getAllNotesData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NOTES,null);
        return res;
    }

    public boolean updateNotesData(String id, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String format = s.format(new Date());
        contentValues.put(COL_NOTES_ID, id);
        contentValues.put(COL_NOTE, note);
        contentValues.put(COL_CREATED, s.toString());
        db.update(TABLE_NOTES, contentValues, "NOTES_ID = ?", new String[] {id});
        return true;
    }

    public boolean updateEventsData(String id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        db.update(TABLE_EVENTS, contentValues, "EVENTS_ID = ?", new String[] {id});
        return true;
    }

    public Integer deleteNotesData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NOTES,"NOTES_ID = ?", new String [] {id});
    }

    public Integer deleteEventsData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        //ensures we delete the notes associated with an event
        db.delete(TABLE_NOTES,COL_EVENTS_ID+" = ?",   new String [] {id});
        return db.delete(TABLE_EVENTS,"NOTES_ID = ?", new String [] {id});
    }

}
