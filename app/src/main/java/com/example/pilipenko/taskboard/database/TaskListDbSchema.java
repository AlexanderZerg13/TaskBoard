package com.example.pilipenko.taskboard.database;

public class TaskListDbSchema {
    public static final class TasksTable {
        public static final String NAME = "tasks";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String TIME = "time";
            public static final String SOLVED = "solved";
        }
    }

    public static final class SpinnerTimePickerTable {
        public static final String NAME = "spinner";

        public static final class Cols {
            public static final String MINUTES = "minute";
        }
    }
}
