package com.example.pilipenko.taskboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class TaskActivity extends AppCompatActivity {

    private Button mButtonAdd;
    private EditText mEditTextTitle;
    private Spinner mSpinnerTime;

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.time_items, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mSpinnerTime.setAdapter(adapter);
        mSpinnerTime.setSelection(0);
        mSpinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
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

    private void addTask() {
        String title = mEditTextTitle.getText().toString();
        TaskLab.get(TaskActivity.this).addTask(title);
        setResult(RESULT_OK);
        finish();
    }
}
