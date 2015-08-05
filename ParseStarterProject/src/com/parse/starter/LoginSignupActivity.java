package com.parse.starter;

/**
 * Created by yiningchen on 7/1/15.
 */

import android.app.Activity;
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

public class LoginSignupActivity extends Activity {
    // Declare Variables
    Button loginbutton;
    Button register;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.loginsignup);
        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        // Locate Buttons in main.xml
        loginbutton = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        // Login Button Click Listener
        loginbutton.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString().toLowerCase();
                passwordtxt = password.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    if (!(user.getBoolean("emailVerified")==true)) {
                                        Toast.makeText(getApplicationContext(),"Please check your mailbox and click on the link we sent you to verify your email.",Toast.LENGTH_LONG).show();
                                    } else {
                                        // If user exist and authenticated, send user to NavigationActivity.class
                                        Intent intent = new Intent(LoginSignupActivity.this, NavigationActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "Successfully Logged in", Toast.LENGTH_LONG).show();
                                        finish();
                                     }
                                } else {
                                    Toast.makeText(getApplicationContext(),"Email/Password pair incorrect, please register",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        // Sign up Button Click Listener leads to Registration Page
        register.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                startActivity(new Intent(LoginSignupActivity.this, Register.class));


            }
        });

    }
}
