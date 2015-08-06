package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class CreateGroup extends Activity {

    private Button button2;
    private Spinner spinner1, spinner2,spinnerMonth,spinnerDay,spinnerYear;
    private Spinner spinnerHour,spinnerMinute,spinnerAMPM;
    private Spinner spinnerMaxNum;
    private EditText LocEditTxt,nameEditTxt;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        button2 = (Button) findViewById(R.id.button2);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        spinnerHour = (Spinner) findViewById(R.id.spinnerHour);
        spinnerMinute = (Spinner) findViewById(R.id.spinnerMinute);
        spinnerAMPM = (Spinner) findViewById(R.id.spinnerAMPM);
        spinnerMaxNum = (Spinner) findViewById(R.id.spinnerMaxNum);
        LocEditTxt = (EditText) findViewById(R.id.LocEditTxt);
        editText = (EditText) findViewById(R.id.editText);
        nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if both name and location fields are unfilled
                if (nameEditTxt.getText().toString().equals("") && LocEditTxt.getText().toString().equals("")) {
                    // make notification to fill in both fields
                    Toast.makeText(getApplicationContext(), "Please fill in name and location fields!", Toast.LENGTH_LONG).show();
                }
                // check if only name is unfilled
                else if (nameEditTxt.getText().toString().equals("")) {
                    // make notification to fill in name field
                    Toast.makeText(getApplicationContext(), "Please fill in group name!", Toast.LENGTH_LONG).show();
                }
                // check if only location is unfilled
                else if (LocEditTxt.getText().toString().equals("")) {
                    // make notification to fill in location field
                    Toast.makeText(getApplicationContext(), "Please fill in meeting location!", Toast.LENGTH_LONG).show();
                }
                // otherwise the group should be able to be created with all the necessary information
                else {
                    //Toast.makeText(getApplicationContext(),
                    //    "Group created",
                    //    Toast.LENGTH_SHORT).show();
                    button2.setText("Group Created");
                    button2.setClickable(false);

                    final ParseUser currentUser = ParseUser.getCurrentUser();

                    final ParseObject NewGroup = new ParseObject("StudyGroups");

                    NewGroup.put("GroupName", nameEditTxt.getText().toString());
                    NewGroup.put("Founder", currentUser.getUsername());
                    NewGroup.put("Department", String.valueOf(spinner1.getSelectedItem()));
                    NewGroup.put("Class", String.valueOf(spinner2.getSelectedItem()));
                    NewGroup.put("Month", String.valueOf(spinnerMonth.getSelectedItem()));
                    NewGroup.put("Day", String.valueOf(spinnerDay.getSelectedItem()));
                    NewGroup.put("Year", String.valueOf(spinnerYear.getSelectedItem()));
                    NewGroup.put("Hour", String.valueOf(spinnerHour.getSelectedItem()));
                    NewGroup.put("Minute", String.valueOf(spinnerMinute.getSelectedItem()));
                    NewGroup.put("AMPM", String.valueOf(spinnerAMPM.getSelectedItem()));
                    NewGroup.put("MaxNum", Integer.parseInt(String.valueOf(spinnerMaxNum.getSelectedItem())));
                    NewGroup.put("CurNum", 1);
                    NewGroup.put("Loc", LocEditTxt.getText().toString());
                    NewGroup.put("Info", editText.getText().toString());
                    NewGroup.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            //myObjectSavedSuccessfully();

                            // Add relation: user to group
                            ParseRelation relation = currentUser.getRelation("MyGroups");
                            relation.add(NewGroup);
                            currentUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        // Add relation: group to user
                                        ParseRelation relation2 = NewGroup.getRelation("MyMembers");
                                        relation2.add(currentUser);
                                        NewGroup.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null)
                                                    System.out.println("group joined!!!");
                                            }
                                        });
                                    } else {
                                        Log.d("weird 2!!!", "Error: " + e.getMessage());
                                    }
                                }
                            });

                        } else {
                            //myObjectSaveDidNotSucceed();
                            Log.d("weird 1!!!", "Error: " + e.getMessage());
                        }
                    }
                });

                //finish();
                }
            }
        });
    }

}