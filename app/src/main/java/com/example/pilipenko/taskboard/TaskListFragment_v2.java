package com.example.pilipenko.taskboard;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskListFragment_v2 extends Fragment {

    private int mNumberTask;

    private static final String TAG = "TAG";

    public static TaskListFragment_v2 newInstance() {

        Bundle args = new Bundle();

        TaskListFragment_v2 fragment = new TaskListFragment_v2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list_v2, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_task_list_v2_linear_layout);


        TaskLab taskLab = TaskLab.get(getActivity());
        ArrayList<Task> tasks = (ArrayList<Task>) taskLab.getTasks();
        mNumberTask = tasks.size() > 3? 3 : tasks.size();
        for(int i = 0; i < mNumberTask; i++) {
            final int n = i;
            final CardView cardView = (CardView) inflater.inflate(R.layout.list_item_task, linearLayout, false);
            TextView textView = (TextView) cardView.findViewById(R.id.list_item_task_text_view);
            textView.setText(tasks.get(i).getTitle());
            linearLayout.addView(cardView);
            final int margin = ((LinearLayout.LayoutParams)cardView.getLayoutParams()).topMargin;

            cardView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Log.i(TAG, "onCreateView: " + cardView.getMeasuredHeight());
                    ObjectAnimator animator = ObjectAnimator
                            .ofFloat(cardView, "y", - cardView.getHeight(), cardView.getY())
                            .setDuration(1900);

                    animator.start();
                }
            });


        }

        return view;
    }

}
