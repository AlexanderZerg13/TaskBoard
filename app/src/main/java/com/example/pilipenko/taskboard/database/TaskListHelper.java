package com.example.pilipenko.taskboard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pilipenko.taskboard.database.TaskListDbSchema.TasksTable;

import java.util.UUID;

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
            TasksTable.Cols.UUID + " string, " +
            TasksTable.Cols.TITLE + " string, " +
            TasksTable.Cols.TIME + " integer, " +
            TasksTable.Cols.SOLVED + " integer" +
            ")");

        ContentValues values;
        for (int i = 0; i < 5; i++) {
            values = new ContentValues();
            values.put(TasksTable.Cols.UUID, UUID.randomUUID().toString());
            values.put(TasksTable.Cols.TITLE, "task #" + i);
            values.put(TasksTable.Cols.TIME, i * 60);
            values.put(TasksTable.Cols.SOLVED, 0);

            db.insert(TasksTable.NAME, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
