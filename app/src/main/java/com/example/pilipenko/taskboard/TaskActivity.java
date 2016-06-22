package com.example.pilipenko.taskboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class TaskActivity extends AppCompatActivity {

    private Button mButtonAdd;
    private EditText mEditTextTitle ;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, TaskActivity.class);
        return intent;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_task_toolbar1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mEditTextTitle = (EditText) findViewById(R.id.activity_task_title);
        mButtonAdd = (Button) findViewById(R.id.activity_task_button_add);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mEditTextTitle.getText().toString();
                TaskLab.get(TaskActivity.this).addTask(title);
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
