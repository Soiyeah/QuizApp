package com.github.sliit.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {

    private EditText txt_r_username;
    private EditText txt_r_email;
    private  EditText txt_r_password;

    private String username;
    private  String email;
    private  String password;

    private Button btn_register;

    QuizDbHelper dbHelper;
    FieldValidator validator;   // create object of FieldValidator class

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

        validator = new FieldValidator();   // initialize the validator

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

        username = txt_r_username.getText().toString();
        email = txt_r_email.getText().toString();
        password = txt_r_password.getText().toString();

        if(!validator.validateUsername(txt_r_username))          // if username field is empty
        {
            Toast.makeText(getApplicationContext(), "Please enter Username!", Toast.LENGTH_LONG).show();
        }
        else if(!validator.validateEmail(txt_r_email))           // if Email field is empty
        {
            Toast.makeText(getApplicationContext(), "Please enter Email!", Toast.LENGTH_LONG).show();
        }
        else if(!validator.validateEmailPattern(txt_r_email))    // if email is invalid
        {
            Toast.makeText(getApplicationContext(), "Email is invalid!", Toast.LENGTH_LONG).show();
        }
        else if(!validator.validatePassword(txt_r_password))      // if password field is empty
        {
            Toast.makeText(getApplicationContext(), "Please enter Password!", Toast.LENGTH_LONG).show();
        }
        else if(!validator.validatePasswordLength(txt_r_password))// if password is too short or too long (Expected length: 4 - 12 characters)
        {
            Toast.makeText(getApplicationContext(), "Please enter a password of 4 - 12 characters", Toast.LENGTH_LONG).show();
        }
        else if(dbHelper.checkUserExist(username))                // if username is already registered
        {
            Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_LONG).show();
        }
        else
        {
            User user = new User(username,email,password,0);    // Create a user object from inserted values
            dbHelper.addUser(user);                                     // add the user to the db

            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);  // Go to the mainActivity (Home screen)
            finish(); //destroy this activity when Registered. so that user cannot come back to the RegisterActivity by pressing back button.
        }

    }


    public void btnClickAlreadyUser(View view)
    {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);  // Go to the LoginActivity
        finish(); //destroy this activity. so that user cannot come back to the RegisterActivity by pressing back button.

    }




}
