package com.github.sliit.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LevelUpActivity extends AppCompatActivity {

    private TextView tv_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_up);


        int value = getIntent().getExtras().getInt("level");
        tv_level = findViewById(R.id.tv_level);
        tv_level.setText("LEVEL "+value);

    }

    public void onClickContinue(View view)
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("response",1);
        setResult(RESULT_OK,resultIntent);
        finish();
    }


}
