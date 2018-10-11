package com.github.sliit.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


// Home Screen
public class MainActivity extends AppCompatActivity
{

    Button start_quiz_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_quiz_btn = findViewById(R.id.btn_start_quiz);

        start_quiz_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }


    private void startQuiz()    // Start QuizActivity when button is clicked
    {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent);
    }


}
