package com.github.sliit.quizapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity
{

    private static long countDownInMillis = 30000;  // 30 SECONDS

    private int marksPerQuestion = 10;
    private static int request_code = 1;

    private TextView tv_score;
    private TextView tv_questionCount;
    private TextView tv_countDown;
    private TextView tv_level;

    private TextView tv_question;
    private RadioGroup rbGroup;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;
    private Button buttonConfirmNext;   // this button works as both confirm answer and next question

    private ColorStateList textColorDefaultRb;

    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int levelCounter;

    private int score;
    private boolean answered;

    private String username;

    private QuizDbHelper dbHelper;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //---------------- initialize the views---------------------//
        tv_score = findViewById(R.id.text_view_score);
        tv_level = findViewById(R.id.text_view_level);
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

        Intent intent = getIntent();    // get the intent sent from MainActivity
        username = intent.getStringExtra("username");   // get the passed username


        textColorDefaultRb = option1.getTextColors();

        dbHelper = new QuizDbHelper(this); // create a QuizDbHelper object

        questionList = dbHelper.getAllQuestions();  // call getAllQuestions method and assign to the question list
        questionCountTotal = questionList.size();   // total number of questions in the list
        Collections.shuffle(questionList);          // Shuffle the question list

        levelCounter = 1;

        showNextQuestion();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!answered)
                {
                    if (option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked())  // if one of the answers is selected
                    {
                        checkAnswer();
                    }
                    else  // if no answers are selected
                    {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
                else  // if answered
                {

                    if((questionCounter % 5) == 0)  // if questionCounter is divisible by 5 ( After every 5 questions )
                    {
                        levelUp();  // new level

                       /// radio button change after confirm fixx
                    }
                    else
                    {
                        showNextQuestion();
                    }

                }
            }
        });


/*
        dbHelper.setHighScore("admin",12);
        dbHelper.getUser("admin");

        dbHelper.setHighScore("admin",25);
        dbHelper.getUser("admin");
*/
    }



    private void showNextQuestion()
    {
        option1.setTextColor(textColorDefaultRb);   //set the color of answers to default color before moving to the next question
        option2.setTextColor(textColorDefaultRb);
        option3.setTextColor(textColorDefaultRb);
        option4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();                       // clear any checked answers before moving to the next question



        if (questionCounter < questionCountTotal)   // if there are any questions left in the question list
        {

            currentQuestion = questionList.get(questionCounter);    //get a new question

            tv_question.setText(currentQuestion.getQuestion());     // display it
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());
            option4.setText(currentQuestion.getOption4());

            questionCounter++;      // increment the question counter

            tv_questionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);

            answered = false;

            buttonConfirmNext.setText("Confirm");

            timeLeftInMillis = countDownInMillis; // assign time left at the start of the question
            startCountDown();

        }
        else    // if all questions are over
        {
            finishQuiz();
        }
    }


    private void startCountDown()
    {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)    // every time the timer ticks
            {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();  //set text
            }

            @Override
            public void onFinish()   // when the time is up
            {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }


    private void updateCountDownText()
    {

        int minutes = (int) (timeLeftInMillis / 1000) / 60;    // convert milli seconds to seconds(timeLeftInMillis/ 1000). then to minutes ( / 60)
                                                               // * minutes is not used in this app *
        int seconds = (int) (timeLeftInMillis / 1000) % 60;    // then get the remaining seconds

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);   // format the time and assign to a string

        tv_countDown.setText(timeFormatted);                    // set it to the text view

    }

    private void checkAnswer()
    {
        answered = true;

        countDownTimer.cancel(); // Cancel the countdown timer if question was answered

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr())  // if the given answer is correct
        {
            score = score + marksPerQuestion;           // increase score (by 10)
            tv_score.setText("Score: " + score);
        }

        showSolution();

    }

    private void levelUp()
    {
        Toast.makeText(getApplicationContext(), "level up!", Toast.LENGTH_LONG).show();
        levelCounter++;                                   // increment levelCounter
        tv_level.setText("Level "+levelCounter);
        //countDownInMillis = countDownInMillis - 5000;   // reduce the time per question in every new level by 5 seconds (level 1: 30sec, level 2: 25sec)
        //marksPerQuestion = marksPerQuestion + 5;        // increase marks per question by 5 in every new level

        Intent intent = new Intent(this,LevelUpActivity.class);
        intent.putExtra("level",levelCounter);
        startActivityForResult(intent,request_code );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        showNextQuestion();
    }

    private void showSolution()
    {
        option1.setTextColor(Color.RED);    // make all the answers color to red
        option2.setTextColor(Color.RED);
        option3.setTextColor(Color.RED);
        option4.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr())  //get the correct answer number of the question
        {
            case 1:                                             // if the correct answer is 1
                option1.setTextColor(Color.GREEN);              // make that answer color Green
                tv_question.setText("Answer 1 is correct");     // replace question text with the correct answer text
                break;
            case 2:
                option2.setTextColor(Color.GREEN);
                tv_question.setText("Answer 2 is correct");
                break;
            case 3:
                option3.setTextColor(Color.GREEN);
                tv_question.setText("Answer 3 is correct");
                break;
            case 4:
                option4.setTextColor(Color.GREEN);
                tv_question.setText("Answer 4 is correct");
                break;
        }

        if (questionCounter < questionCountTotal)   // if there are any questions left
        {
            buttonConfirmNext.setText("Next");      // set button text to next
        }
        else                                        // if questions are over
        {
            buttonConfirmNext.setText("Finish");    // set the button text to finish
        }

    }

    private void updateHighScore()
    {

        if(score > dbHelper.getHighScore(username))
        {
            dbHelper.setHighScore(username,score);
        }
    }


    private void finishQuiz()
    {
        updateHighScore();
        finish();
    }



    @Override
    protected void onDestroy()          // when quiz activity is destroyed
    {
        super.onDestroy();
        if(countDownTimer != null)      //if countdown timer is still running
        {
            countDownTimer.cancel();    // cancel the timer
        }

    }
}



