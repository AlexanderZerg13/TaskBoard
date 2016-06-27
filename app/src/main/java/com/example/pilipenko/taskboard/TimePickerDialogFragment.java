package com.example.pilipenko.taskboard;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class TimePickerDialogFragment extends DialogFragment {

    private static final String KEY_MINUTES = "MINUTES";

    private TimePicker mTimePicker;

    public static TimePickerDialogFragment newInstance(int minutes) {

        Bundle args = new Bundle();
        args.putInt(KEY_MINUTES, minutes);

        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_time_picker, null);

        mTimePicker = (TimePicker) view.findViewById(R.id.fragment_time_picker_timer_picker);
        mTimePicker.setIs24HourView(true);
        int m = getArguments().getInt(KEY_MINUTES, 0);
        mTimePicker.setCurrentHour(m / 60);
        mTimePicker.setCurrentMinute(m % 60);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int minutes = mTimePicker.getCurrentHour() * 60 + mTimePicker.getCurrentMinute();
                        TimePickerDialogListener activity = (TimePickerDialogListener) getActivity();
                        activity.onFinishEnterTime(minutes);
                    }
                })
                .create();
    }


    public interface TimePickerDialogListener {
        void onFinishEnterTime(int minutes);
    }
}