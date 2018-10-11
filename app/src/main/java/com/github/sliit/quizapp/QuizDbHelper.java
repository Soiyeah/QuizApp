package com.github.sliit.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.sliit.quizapp.QuizContract.*;    // Import the QuizContract class

import java.util.ArrayList;
import java.util.List;



// ****************************** This class is used to communicate with the DB *********************************//
//********** We define a set of methods to create tables, insert values and to retrieve data from the DB *********//



public class QuizDbHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "QuizDB.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;


    public QuizDbHelper(Context context)    // QuizDbHelper constructor
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //-- ONLY CALLED WHEN APP IS INSTALLED FOR THE 1ST TIME --//
    @Override
    public void onCreate(SQLiteDatabase db)
    {

        Log.d("D","Quizdb helper oncreate() method called");


        this.db = db; // Create the database

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +             // SQL query to create the Questions table
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        // "QuestionsTable.COLUMN_QUESTION" - predefined names in the QuizContract class
        // Ex: public static final String COLUMN_QUESTION = "question";
        // So the column name in the database is "question"


        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " +             // SQL query to create the User table
                UserTable.TABLE_NAME + " ( " +
                UserTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " +
                UserTable.COLUMN_EMAIL + " TEXT, " +
                UserTable.COLUMN_PASSWORD + " TEXT, " +
                UserTable.COLUMN_HIGHSCORE + " INTEGER" +
                ")";


        db.execSQL(SQL_CREATE_QUESTIONS_TABLE); // Execute the query to create the Questions table in the DB
        fillQuestionsTable();   // Call the method to add Questions to the table

        db.execSQL(SQL_CREATE_USER_TABLE); // Execute the query to create the User table in the DB
        fillUserTable(); // Call the method to add Admin user to the table

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)    // Called ONLY if the Database is changed
    {
        Log.d("D","On upgrade() called");
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE_NAME);
        onCreate(db);
    }


    //================================== Methods for Question Table ==========================================//


    //-- ONLY CALLED WHEN APP IS INSTALLED FOR THE 1ST TIME --//
    private void fillQuestionsTable()   // To add values to the Question object
    {

        Log.d("D","fillQuestionsTable() method called");

        // Create Question objects
        Question q1 = new Question("This is the Question 1. the answer is 1", "Answer 1", "Answer 2", "Answer 3", "Answer 4", 1);
        addQuestion(q1);
        Question q2 = new Question("This is the Question 2. the answer is 3", "Answer 1", "Answer 2", "Answer 3", "Answer 4", 3);
        addQuestion(q2);
        Question q3 = new Question("This is the Question 3. the answer is 2", "Answer 1", "Answer 2", "Answer 3", "Answer 4", 2);
        addQuestion(q3);
        Question q4 = new Question("This is the Question 4. the answer is 4", "Answer 1", "Answer 2", "Answer 3", "Answer 4", 4);
        addQuestion(q4);
        Question q5 = new Question("This is the Question 5. the answer is 1", "Answer 1", "Answer 2", "Answer 3", "Answer 4", 1);
        addQuestion(q5);
        Question q6 = new Question("This is the Question 6. the answer is 2", "Answer 1", "Answer 2", "Answer 3", "Answer 4", 2);
        addQuestion(q6);
        Question q7 = new Question("This is the Question 7. the answer is 1", "Answer 1", "Answer 2", "Answer 3", "Answer 4", 1);
        addQuestion(q7);
        Question q8 = new Question("This is the Question 8. the answer is 4", "Answer 1", "Answer 2", "Answer 3", "Answer 4", 4);
        addQuestion(q8);
    }

    //-- ONLY CALLED WHEN APP IS INSTALLED FOR THE 1ST TIME --//
    private void addQuestion(Question question)
    {
        ContentValues cv = new ContentValues(); //ContentValue class lets you put information inside an object in the form of Key-Value pairs for columns and their value.

        // get values from the question object and assign to the Content value object
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);        //Insert to the DB
    }

    public List<Question> getAllQuestions()     // this method returns the List of questions
    {
        List<Question> questionList = new ArrayList<>();    // create a Question list
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null); // SQL query to select all the questions

        if (c.moveToFirst()) // Move to the 1st row of the question table
        {
            do {
                Question question = new Question(); // create a new question object for each row in question table
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));   // Assign values to the question object
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);     // Add question object to the question List
            } while (c.moveToNext());    // move to next row until end of the table
        }

        c.close();  // close the cursor
        return questionList;
    }

    //================================== Methods for User Table ==========================================//

    //-- ONLY CALLED WHEN APP IS INSTALLED FOR THE 1ST TIME --//
    private void fillUserTable()   // To add values to the user object
    {
        // Create User objects
        User u1 = new User("admin","admin@gmail.com","admin",10); // 'admin' user added for testing
        addUser(u1);
    }


    public void addUser(User user)
    {
        ContentValues cv = new ContentValues(); //ContentValue class lets you put information inside an object in the form of Key-Value pairs for columns and their value.

        // get values from the user object and assign to the Content value object
        cv.put(UserTable.COLUMN_USERNAME, user.getUsername());
        cv.put(UserTable.COLUMN_EMAIL, user.getEmail());
        cv.put(UserTable.COLUMN_PASSWORD, user.getPassword());
        db.insert(UserTable.TABLE_NAME, null, cv);     //Insert to the DB
    }

    public boolean checkUserExist(String username) // check whether the username already exist
    {
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + UserTable.TABLE_NAME + " WHERE " + UserTable.COLUMN_USERNAME + " = " + "'" +  username + "'" , null);

        if (c != null && c.moveToFirst()&& c.getCount()>0) // if the username is in the table
        {
            c.close();
            return true;
        }
        else
        {
            c.close();
            return false;
        }
    }

}
