package com.example.pilipenko.taskboard.database;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.pilipenko.taskboard.R;
import com.example.pilipenko.taskboard.database.TaskListDbSchema.SpinnerTimePickerTable;

public class SpinnerCursorWrapper extends CursorWrapper{

    public SpinnerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public static String getItemText(int value, Context context) {
        if (value < 60) {
            return context.getResources().getQuantityString(R.plurals.minute_plurals, value, value);
        } else {
            if (value % 60 == 0) {
                int hours = value / 60;
                return context.getResources().getQuantityString(R.plurals.hour_plurals, hours, hours);
            } else {
                int minutes = value % 60;
                int hours = value / 60;
                return context.getResources().getString(R.string.hour_and_minute, hours, minutes);
            }
        }
    }

    public int getItemValue() {
        int value = getInt(getColumnIndex(SpinnerTimePickerTable.Cols.MINUTES));
        return value;
    }
}
