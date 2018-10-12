package com.github.sliit.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_l_username;
    private EditText txt_l_password;

    private Button btn_login;

    private String username;
    private  String password;

    QuizDbHelper dbHelper;
    FieldValidator validator;   // create object of FieldValidator class

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_l_username = findViewById(R.id.txt_login_username);
        txt_l_password = findViewById(R.id.txt_login_password);

        btn_login = findViewById(R.id.btn_login);

        dbHelper = new QuizDbHelper(this); // create a QuizDbHelper object
        dbHelper.getReadableDatabase();

        validator = new FieldValidator();   // initialize the validator

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                login();
            }
        });

    }

    public void login() //login to the app
    {


        if(!validator.validateUsername(txt_l_username))          // if username field is empty
        {
            Toast.makeText(getApplicationContext(), "Please enter Username!", Toast.LENGTH_LONG).show();
        }
        else if(!validator.validatePassword(txt_l_password))      // if password field is empty
        {
            Toast.makeText(getApplicationContext(), "Please enter Password!", Toast.LENGTH_LONG).show();
        }
        else
        {
            username = txt_l_username.getText().toString();
            password = txt_l_password.getText().toString();

            if(dbHelper.checkUserExist(username))   // check whether the username exists in the user table
            {
                User user = dbHelper.getUser(username); // get all information of user

                if(user.getPassword().equals(password)) // if entered password equals to the password in the user object
                {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);  // Go to the mainActivity (Home screen)
                    finish(); //destroy this activity when logged in. so that user cannot come back to the LoginActivity by pressing back button.
                }
                else    // if username exists but the passwords don't match
                {
                    Toast.makeText(getApplicationContext(), "incorrect password", Toast.LENGTH_LONG).show();
                }
            }
            else    // if username doesn't exist in the user table
            {
                Toast.makeText(getApplicationContext(), "User doesn't exist! Register to continue", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void btnClickNewUser(View view)
    {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);  // Go to the Register activity
        finish(); //destroy this activity. so that user cannot come back to the LoginActivity by pressing back button.
    }



}
