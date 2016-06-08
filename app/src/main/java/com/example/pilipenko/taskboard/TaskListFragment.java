package com.example.pilipenko.taskboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class TaskListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;

    public static TaskListFragment newInstance() {

        Bundle args = new Bundle();

        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_task_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);

        updateUI();

        return view;
    }

    private void updateUI() {
        TaskLab taskLab = TaskLab.get(getActivity());
        List<Task> tasks = taskLab.getTasks();
        mAdapter = new TaskAdapter(tasks);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Task mTask;
        private int mPosition;
        private TextView mTitleTextView;
        private ImageButton mButtonDone;
        private ImageButton mButtonSwap;

        public TaskHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_task_text_view);
            mButtonDone = (ImageButton) itemView.findViewById(R.id.list_item_task_button_done);
            mButtonSwap = (ImageButton) itemView.findViewById(R.id.list_item_task_button_swipe);

            mButtonDone.setOnClickListener(this);
            mButtonSwap.setOnClickListener(this);
        }

        public void bindTask(Task task) {
            mTask = task;
            mTitleTextView.setText(task.getTitle());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.list_item_task_button_done:
                    mAdapter.notifyItemRemoved(getLayoutPosition());
                    mAdapter.notifyItemInserted(0);
                    break;
                case R.id.list_item_task_button_swipe:
                    mAdapter.notifyItemRemoved(getLayoutPosition());
                    mAdapter.notifyItemInserted(0);
                    break;
            }
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_item_task, parent, false);

            return new TaskHolder(v);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = mTasks.get(position);

            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}
