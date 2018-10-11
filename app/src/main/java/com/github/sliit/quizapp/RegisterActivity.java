package com.github.sliit.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText txt_r_username;
    private EditText txt_r_email;
    private  EditText txt_r_password;

    private String username;
    private  String email;
    private  String password;

    private Button btn_register;

    QuizDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_r_username = findViewById(R.id.txt_register_username);
        txt_r_email = findViewById(R.id.txt_register_email);
        txt_r_password = findViewById(R.id.txt_register_password);

        btn_register = findViewById(R.id.btn_register);

        dbHelper = new QuizDbHelper(this); // create a QuizDbHelper object
        dbHelper.getReadableDatabase();

        btn_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });


    }

    private void registerNewUser()
    {
        // +++++++++++ validate register form ++++++++++++//

        username = txt_r_username.getText().toString();
        email = txt_r_email.getText().toString();
        password = txt_r_password.getText().toString();

        User user = new User(username,email,password,0);
        dbHelper.addUser(user);

        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
    }



}
