package com.example.yachtgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//    점수 기록 DB
class DBHelper extends SQLiteOpenHelper {

    final static int ID_COLUMN = 0;
    final static int NAME_COLUMN = 1;
    final static int SCORE_COLUMN = 2;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table if not exists ScoreBoard (_id INTEGER primary key autoincrement, Name TEXT, Score INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists ScoreBoard");
        onCreate(db);
    }
}