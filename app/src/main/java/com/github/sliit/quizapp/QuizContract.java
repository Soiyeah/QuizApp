package com.github.sliit.quizapp;

/*--------- Defining column names of the tables ------------- */

import android.provider.BaseColumns;

public final class QuizContract
{

    private QuizContract()  // default Constructor
    {
    }

    // An inner class is created for each table in the DB

    public static class QuestionsTable implements BaseColumns   // Question Table column names
    {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }

    // An inner class is created for each table in the DB

    public static class UserTable implements BaseColumns    // User Table column names
    {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_HIGHSCORE = "highscore";
    }



}
