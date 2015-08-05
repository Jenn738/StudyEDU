package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;
import com.parse.PushService;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "1RaZa8VhErCxW7G0nxv7Jsj3Hmfe6YNmSntmunN9", "0gkr0Pd1WNVpwJOyssBMn7QceNifTSCuVTDJ1zVS");

    ParseUser.enableAutomaticUser();
    //ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    //defaultACL.setPublicReadAccess(true);
    //defaultACL.setPublicWriteAccess(true);
    //ParseACL.setDefaultACL(defaultACL, true);
  }
}
