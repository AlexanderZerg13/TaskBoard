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

public class TimePickerDialogFragment extends DialogFragment {
    private String[] mTypes;

    private EditText mEditText;
    private Spinner mSpinner;
    private ArrayAdapter mArrayAdapter;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_time_picker, null);
        mTypes = getResources().getStringArray(R.array.time_type);

        mEditText = (EditText) view.findViewById(R.id.fragment_time_picker_edit_text_time);
        mSpinner = (Spinner) view.findViewById(R.id.fragment_time_picker_spinner_type);
        mArrayAdapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.time_type, android.R.layout.simple_spinner_item);

        mArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mArrayAdapter);
        mSpinner.setSelection(0);


        initListener();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.select_time)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(mEditText.getText())) {
                            return;
                        }
                        int time = Integer.parseInt(mEditText.getText().toString());
                        int type = mSpinner.getSelectedItemPosition() == 0?
                                TimePickerDialogListener.KEY_TYPE_MINUTE: TimePickerDialogListener.KEY_TYPE_HOUR;
                        TimePickerDialogListener activity = (TimePickerDialogListener) getActivity();
                        activity.onFinishEnterTime(time, type);
                    }
                })
                .create();
    }

    private void initListener() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkMaxHour(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    checkMaxHour(mEditText.getText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void checkMaxHour(CharSequence s) {
        if (mTypes[1].equals(mArrayAdapter.getItem(mSpinner.getSelectedItemPosition()))) {
            if (TextUtils.isEmpty(s)) {
                return;
            }
            int i = Integer.parseInt(s.toString());
            if (i > 24) {
                mEditText.setText("24");
                mEditText.setSelection(2);
            }
        }
    }

    public interface TimePickerDialogListener {
        int KEY_TYPE_MINUTE = 0;
        int KEY_TYPE_HOUR = 1;

        void onFinishEnterTime(int time, int type);
    }
}