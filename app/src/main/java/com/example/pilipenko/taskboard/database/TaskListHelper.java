package com.example.pilipenko.taskboard.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pilipenko.taskboard.database.TaskListDbSchema.TasksTable;

public class TaskListHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";

    public TaskListHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TasksTable.NAME + "(" +
            "_id integer primary key autoincrement, " +
            TasksTable.Cols.TITLE + " string, " +
            TasksTable.Cols.TIME + " integer, " +
            TasksTable.Cols.SOLVED + " integer" +
            ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
