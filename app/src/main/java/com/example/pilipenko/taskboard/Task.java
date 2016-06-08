package com.example.pilipenko.taskboard;

import java.util.UUID;

public class Task {
    private UUID mId;
    private String mTitle;
    private int mTime;
    private boolean mSolved;

    public Task() {
        mId = UUID.randomUUID();
    }

    public Task(UUID id) {
        mId = id;
    }

    public Task(String title, int time, boolean solved) {
        super();
        mTitle = title;
        mTime = time;
        mSolved = solved;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
