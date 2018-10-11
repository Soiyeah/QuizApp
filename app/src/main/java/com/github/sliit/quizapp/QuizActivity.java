package com.github.sliit.quizapp;

import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class QuizActivity extends AppCompatActivity
{

    private TextView tv_score;
    private TextView tv_questionCount;
    private TextView tv_countDown;

    private TextView tv_question;
    private RadioGroup rbGroup;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;
    private Button buttonConfirmNext;


    private ColorStateList textColorDefaultRb;

    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;

    private QuizDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //---------------- initialize the views---------------------//
        tv_score = findViewById(R.id.text_view_score);
        tv_questionCount = findViewById(R.id.text_view_question_count);
        tv_countDown = findViewById(R.id.text_view_countdown);
        tv_question = findViewById(R.id.text_view_question);
        rbGroup = findViewById(R.id.radio_group);
        option1 = findViewById(R.id.rb_option1);
        option2 = findViewById(R.id.rb_option2);
        option3 = findViewById(R.id.rb_option3);
        option4 = findViewById(R.id.rb_option4);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);
        //-------------------------------------------------------------//

        textColorDefaultRb = option1.getTextColors();

        dbHelper = new QuizDbHelper(this); // create a QuizDbHelper object
        dbHelper.getWritableDatabase();
        questionList = dbHelper.getAllQuestions();  // call getAllQuestions method and assign to the question list

        questionCountTotal = questionList.size();   //total number of questions in the list

        tv_questionCount.setText("Total qz :"+questionCountTotal);

/*
        dbHelper.setHighScore("admin",12);
        dbHelper.getUser("admin");

        dbHelper.setHighScore("admin",25);
        dbHelper.getUser("admin");
*/
    }






}
