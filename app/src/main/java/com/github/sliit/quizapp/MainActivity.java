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

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        start_quiz_btn = findViewById(R.id.btn_start_quiz);
        lbl_welcome = findViewById(R.id.lbl_welcome);

        lbl_welcome.setText("Welcome," + username);

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
