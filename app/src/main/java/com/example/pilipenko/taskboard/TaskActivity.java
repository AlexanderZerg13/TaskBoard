package com.example.pilipenko.taskboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class TaskActivity extends AppCompatActivity implements TimePickerDialogFragment.TimePickerDialogListener {

    private static final String DIALOG_TIME = "DialogTime";

    private Button mButtonAdd;
    private EditText mEditTextTitle;
    private Spinner mSpinnerTime;
    private ArrayAdapter<CharSequence> mArrayAdapter;

    private int oldPosition;
    private int extraTime;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, TaskActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_task_toolbar1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mEditTextTitle = (EditText) findViewById(R.id.activity_task_title);
        mSpinnerTime = (Spinner) findViewById(R.id.activity_task_spinner_time);

        mArrayAdapter = new ArrayAdapter<CharSequence>(this, R.layout.simple_spinner_item);
                //ArrayAdapter.createFromResource(this, R.array.time_items, R.layout.simple_spinner_item);
        mArrayAdapter.addAll(getResources().getStringArray(R.array.time_items));
        mArrayAdapter.add(getString(R.string.time_items_other));
        mArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        mSpinnerTime.setAdapter(mArrayAdapter);
        mSpinnerTime.setSelection(0);
        mSpinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                if (mArrayAdapter.getCount() == position + 1) {
                    mSpinnerTime.setSelection(oldPosition);
                    FragmentManager manager = getSupportFragmentManager();
                    if (manager.findFragmentByTag(DIALOG_TIME) != null) {
                        return;
                    }
                    TimePickerDialogFragment dialogFragment = new TimePickerDialogFragment();
                    dialogFragment.show(manager, DIALOG_TIME);
                } else {
                    oldPosition = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mButtonAdd = (Button) findViewById(R.id.activity_task_button_add);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.task_activity_menu_add:
                addTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFinishEnterTime(int time, int type) {
        mArrayAdapter.clear();
        mArrayAdapter.addAll(getResources().getStringArray(R.array.time_items));

        StringBuilder stringBuilder = new StringBuilder();
        if (type == TimePickerDialogFragment.TimePickerDialogListener.KEY_TYPE_HOUR) {
            stringBuilder.append(time);
            stringBuilder.append(" hours");
            extraTime = time * 60;
        } else if (type == TimePickerDialogFragment.TimePickerDialogListener.KEY_TYPE_MINUTE) {
            if (time < 60) {
                stringBuilder.append(time);
                stringBuilder.append(" minutes");
            } else {
                if (time % 60 == 0) {
                    stringBuilder.append(time / 60);
                    stringBuilder.append(" hours");
                } else {
                    stringBuilder.append(time / 60);
                    stringBuilder.append(" h. ");
                    stringBuilder.append(time % 60);
                    stringBuilder.append(" m.");
                }
            }
            extraTime = time;
        }
        mArrayAdapter.add(stringBuilder.toString());

        mArrayAdapter.add(getString(R.string.time_items_other));
        mArrayAdapter.notifyDataSetChanged();


        mSpinnerTime.setSelection(mArrayAdapter.getCount() - 2);
    }

    private void addTask() {
        int time;
        int[] array = getResources().getIntArray(R.array.array_minutes);
        int position = mSpinnerTime.getSelectedItemPosition();
        if (position == array.length) {
            time = extraTime;
        } else {
            time = array[position];
        }

        String title = mEditTextTitle.getText().toString();
        TaskLab.get(TaskActivity.this).addTask(title, time);
        setResult(RESULT_OK);
        finish();
    }
}
