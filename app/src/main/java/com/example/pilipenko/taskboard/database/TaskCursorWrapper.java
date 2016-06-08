package com.example.pilipenko.taskboard.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.pilipenko.taskboard.Task;
import com.example.pilipenko.taskboard.database.TaskListDbSchema.TasksTable;

import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {

    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String uuidString = getString(getColumnIndex(TasksTable.Cols.UUID));
        String title = getString(getColumnIndex(TasksTable.Cols.TITLE));
        int time = getInt(getColumnIndex(TasksTable.Cols.TIME));
        int isSoled = getInt(getColumnIndex(TasksTable.Cols.SOLVED));

        Task task = new Task(UUID.fromString(uuidString));
        task.setTitle(title);
        task.setTime(time);
        task.setSolved(isSoled != 0);

        return task;
    }
}
