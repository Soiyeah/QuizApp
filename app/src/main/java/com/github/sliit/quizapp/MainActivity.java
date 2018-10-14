package com.github.sliit.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


// Home Screen
public class MainActivity extends AppCompatActivity
{

    private Button start_quiz_btn;
    private TextView lbl_welcome;
    private TextView lbl_your_highscore;
    private TextView lbl_overall_highscore;

    private int highscore;
    private int overallhighscore;
    private QuizDbHelper dbHelper;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new QuizDbHelper(this); // create a QuizDbHelper object

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        start_quiz_btn = findViewById(R.id.btn_start_quiz);
        lbl_welcome = findViewById(R.id.lbl_welcome);
        lbl_your_highscore = findViewById(R.id.txt_your_highscore);
        lbl_overall_highscore = findViewById(R.id.txt_overall_highscore);

        lbl_welcome.setText("Welcome, " + username);

        updateHighScoreText();

        start_quiz_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }

    private void updateHighScoreText()
    {
        highscore = dbHelper.getHighScore(username);
        lbl_your_highscore.setText(highscore+"");

        overallhighscore = dbHelper.getOverallHighScore();      // best high score from all the users
        lbl_overall_highscore.setText(overallhighscore+"");
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateHighScoreText();
    }

    private void startQuiz()    // Start QuizActivity when button is clicked
    {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("username", username);  // pass the username to the quizActivity
        startActivity(intent);  // Go to the quizActivity

    }


}
