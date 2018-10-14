package com.github.sliit.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class LevelUpActivity extends AppCompatActivity {

    private TextView tv_level;
    private TextView tv_time;
    private TextView tv_marks;

    private int level;
    private long time;
    private int marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_up);

        level = getIntent().getExtras().getInt("level");    // get the level from the quizActivity
        time = getIntent().getExtras().getLong("timePerQuestion");    // get the level from the quizActivity
        marks = getIntent().getExtras().getInt("marksPerQuestion");    // get the level from the quizActivity

        int seconds = (int) (time / 1000) % 60;    // then get the remaining seconds
        String timeFormatted = String.format(Locale.getDefault(), "%02d", seconds);   // format the time and assign to a string

        tv_level = findViewById(R.id.tv_level);
        tv_time = findViewById(R.id.tv_time);
        tv_marks = findViewById(R.id.tv_marks);

        tv_level.setText("LEVEL " + level);
        tv_time.setText("Time per question: " + timeFormatted + " seconds");
        tv_marks.setText("Marks per question: " + marks);

    }

    public void onClickContinue(View view)
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("response",1);
        setResult(RESULT_OK,resultIntent);  // send back "OK"
        finish();
    }


}
