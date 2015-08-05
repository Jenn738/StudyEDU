package com.parse.starter;

import android.view.View;

import android.app.Activity;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Objects;
import java.util.Random;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Register extends Activity {
    // Declare Variables
    Button signup;
    String usernametxt;
    String passwordtxt;
    String password2txt;
    EditText password;
    EditText username;
    EditText password2;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.register);
        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);

        // Locate Buttons in main.xml
        signup = (Button) findViewById(R.id.signup);

        signup.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString().toLowerCase();
                passwordtxt = password.getText().toString();
                password2txt = password2.getText().toString();

                // Force user to fill up the form
                if (usernametxt.equals("") || passwordtxt.equals("") || password2txt.equals("")) {
                       Toast.makeText(getApplicationContext(), "Please complete the registration form", Toast.LENGTH_LONG).show();
                   }
                else if ((!Objects.equals(passwordtxt, password2txt))) {
                    Toast.makeText(getApplicationContext(), "Please make sure your passwords match", Toast.LENGTH_LONG).show();
                }
                else if (usernametxt.contains("@")){
                    Toast.makeText(getApplicationContext(), "Please enter the part before @dartmouth.edu only", Toast.LENGTH_LONG).show();
                }
                else {
                    String useremail = usernametxt + "@dartmouth.edu";

                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    //user.setEmail(emailtxt);
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);
                    user.setEmail(useremail);
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                Toast.makeText(getApplicationContext(), "Please check your mailbox and click on the link we sent you to verify your email.", Toast.LENGTH_LONG).show();

                                // If user exist and authenticated, send user to NavigationActivity.class
                                //Intent intent = new Intent(Register.this,NavigationActivity.class);
                                //startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Sign up Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }


        });
    }
}
