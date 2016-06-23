package com.example.pilipenko.taskboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pilipenko.taskboard.database.TaskCursorWrapper;
import com.example.pilipenko.taskboard.database.TaskListDbSchema.TasksTable;
import com.example.pilipenko.taskboard.database.TaskListHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskLab {
    private static TaskLab sTaskLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TaskLab get(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab(context);
        }
        return sTaskLab;
    }

    private TaskLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TaskListHelper(mContext)
                .getWritableDatabase();
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();

        String whereClause = TasksTable.Cols.SOLVED + " = ?";
        String[] whereArgs = {"0"};
        TaskCursorWrapper cursor = queryTask(whereClause, whereArgs);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return tasks;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        TaskCursorWrapper cursor = queryTask(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return tasks;
    }

    public boolean doneTask(UUID uuid) {
        String whereClause = TasksTable.Cols.UUID + " = ?";
        String[] whereArgs = {uuid.toString()};
        TaskCursorWrapper cursor = queryTask(whereClause, whereArgs);
        cursor.moveToFirst();
        Task task = cursor.getTask();
        task.setSolved(true);
        ContentValues contentValues = getContentValues(task);
        int update = mDatabase.update(TasksTable.NAME, contentValues, whereClause, whereArgs);
        return update == 1? true: false;
    }

    public long addTask(String title, int minutes) {
        Log.d("TAG", "addTask: " + title + " " + minutes);
        Task task = new Task(title, minutes, false);
        ContentValues contentValues = getContentValues(task);
        return mDatabase.insert(TasksTable.NAME, null, contentValues);
    }

    private TaskCursorWrapper queryTask(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TasksTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new TaskCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TasksTable.Cols.UUID, task.getId().toString());
        values.put(TasksTable.Cols.TITLE, task.getTitle());
        values.put(TasksTable.Cols.TIME, task.getTime());
        values.put(TasksTable.Cols.SOLVED, task.isSolved() ? 1 : 0);

        return values;
    }
}
