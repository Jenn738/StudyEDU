package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import android.app.Activity;
import android.content.Intent;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.Date;
import java.util.Arrays;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
/*
        // Dummy positions
        ParseObject Nan = new ParseObject("DataBase");
        Nan.put("UserName", "NH");
        Nan.put("longitude", 25.1);
        Nan.put("latitude", 36.2);
        Nan.saveInBackground();

        ParseObject Jenn = new ParseObject("DataBase");
        Jenn.put("UserName", "JJ"); // pretty much key-value pairs
        Jenn.put("longitude", 19.1);
        Jenn.put("latitude", 44.2);
        Jenn.saveInBackground(); // send to Parse

        ParseObject Hugh = new ParseObject("DataBase");
        Hugh.put("UserName", "HL");
        Hugh.put("longitude", 37.1);
        Hugh.put("latitude", 12.2);
        Hugh.saveInBackground();

        ParseObject Yin = new ParseObject("DataBase");
        Yin.put("UserName", "YC");
        Yin.put("longitude", 2.1);
        Yin.put("latitude", 50.2);
        Yin.saveInBackground();
*/
        // Determine whether the current user is an anonymous user
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to LoginSignupActivity.class
            Intent intent = new Intent(MainActivity.this,
                    LoginSignupActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to NavigationActivity.class
                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(MainActivity.this,
                        LoginSignupActivity.class);
                startActivity(intent);
                finish();
            }
        }

		//ParseAnalytics.trackAppOpenedInBackground(getIntent());

	}
}
