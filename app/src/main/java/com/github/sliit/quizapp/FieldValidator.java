package com.github.sliit.quizapp;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class FieldValidator
{

    public FieldValidator()
    {

    }

    public boolean validateUsername(EditText username)      // Check whether the username field is empty
    {
        if (TextUtils.isEmpty(username.getText().toString()))
        {
            username.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean validateEmail(EditText email)              // Check whether the email field is empty
    {
        if (TextUtils.isEmpty(email.getText().toString()))
        {
            email.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean validateEmailPattern(EditText email)     // Check whether entered email is valid
    {
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
        {
            email.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean validatePassword(EditText password)     // Check whether the password field is empty
    {
        if (TextUtils.isEmpty(password.getText().toString().trim()))
        {
            password.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }


    public boolean validatePasswordLength(EditText password)     // Check whether the password is valid in length
    {
        if (password.getText().toString().trim().length() < 4 || password.getText().toString().trim().length() > 12)
        {
            password.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }



}
