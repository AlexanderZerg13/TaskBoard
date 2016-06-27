package com.example.pilipenko.taskboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, TaskActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        final TaskLab taskLab = TaskLab.get(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_task_toolbar1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mEditTextTitle = (EditText) findViewById(R.id.activity_task_title);
        mSpinnerTime = (Spinner) findViewById(R.id.activity_task_spinner_time);

        mArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item);
        mArrayAdapter.addAll(taskLab.getSpinnerItems());
        mArrayAdapter.add(getString(R.string.time_items_other));
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        mSpinnerTime.setAdapter(mArrayAdapter);
        mSpinnerTime.setSelection(0);
        mSpinnerTime.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
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
                    int value = taskLab.getSpinnerHistoryItem();
                    TimePickerDialogFragment dialogFragment = TimePickerDialogFragment.newInstance(value);
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
    public void onFinishEnterTime(int minutes) {
        TaskLab taskLab = TaskLab.get(this);
        taskLab.addSpinnerHistoryItem(minutes);

        mArrayAdapter.clear();
        mArrayAdapter.addAll(taskLab.getSpinnerItems());
        mArrayAdapter.add(getString(R.string.time_items_other));

        mArrayAdapter.notifyDataSetChanged();

        mSpinnerTime.setSelection(mArrayAdapter.getCount() - 2);
    }

    private void addTask() {
        TaskLab taskLab = TaskLab.get(this);
        int position = mSpinnerTime.getSelectedItemPosition();
        int time = taskLab.getSpinnerItemsValues().get(position);

        String title = mEditTextTitle.getText().toString();
        taskLab.addTask(title, time);
        setResult(RESULT_OK);
        finish();
    }
}
