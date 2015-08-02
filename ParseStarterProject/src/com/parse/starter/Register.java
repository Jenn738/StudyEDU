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
    Button sendemail;
    String usernametxt;
    String passwordtxt;
    String password2txt;
    String verificationcodetxt;
    EditText password;
    EditText username;
    EditText verificationcode;
    EditText password2;
    int secretcode;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.register);
        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.email);
        verificationcode = (EditText) findViewById(R.id.verificationcode);

        // Button that sends email verification

        sendemail = (Button) findViewById(R.id.sendemail);
        sendemail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        usernametxt = username.getText().toString().toLowerCase();
                        Random rand = new Random();
                        secretcode = rand.nextInt(900000) + 100000;

                        if (usernametxt.equals("")) {
                            Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_LONG).show();
                        } else if (usernametxt.contains("@")) {
                            Toast.makeText(getApplicationContext(), "Please enter the part before @dartmouth.edu only", Toast.LENGTH_LONG).show();
                        } else {

                            // Recipient's email ID needs to be mentioned.
                            String to = usernametxt + "@dartmouth.com";

                            // Sender's email ID needs to be mentioned
                            String from = "dartmapthem@gmail.com";

                            // Assuming you are sending email from localhost
                            String host = "localhost";

                            // Get system properties
                            Properties properties = System.getProperties();

                            // Setup mail server
                            properties.setProperty("mail.smtp.host", host);

                            // Get the default Session object.
                            Session session = Session.getDefaultInstance(properties);

                            try{
                                // Create a default MimeMessage object.
                                MimeMessage message = new MimeMessage(session);

                                // Set From: header field of the header.
                                message.setFrom(new InternetAddress(from));

                                // Set To: header field of the header.
                                message.addRecipient(Message.RecipientType.TO,
                                        new InternetAddress(to));

                                // Set Subject: header field
                                message.setSubject("Email_Verification_Code");

                                // Now set the actual message
                                message.setText("OK");

                                // Send message
                                Transport.send(message);
                                System.out.println("Sent message successfully....");
                            }catch (MessagingException mex) {
                                mex.printStackTrace();
                            }
                        }
                    }
                }).start();


            }
        });

        password = (EditText) findViewById(R.id.password);
//        email = (EditText) findViewById(R.id.email);
        password2 = (EditText) findViewById(R.id.password2);

        // Locate Buttons in main.xml
        signup = (Button) findViewById(R.id.signup);

        signup.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString().toLowerCase();
                verificationcodetxt = verificationcode.getText().toString();
                passwordtxt = password.getText().toString();
//                emailtxt = email.getText().toString();
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
                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    //user.setEmail(emailtxt);
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                Toast.makeText(getApplicationContext(), "Successfully Signed up.", Toast.LENGTH_LONG).show();

                                // If user exist and authenticated, send user to Welcome.class
                                Intent intent = new Intent(Register.this,NavigationActivity.class);
                                startActivity(intent);
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
