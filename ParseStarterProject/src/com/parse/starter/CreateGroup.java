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

import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;


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
                Toast.makeText(getApplicationContext(),
                        "Group created",
                        Toast.LENGTH_SHORT).show();

                ParseUser currentUser = ParseUser.getCurrentUser();

                ParseObject NewGroup = new ParseObject("StudyGroups");

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
                NewGroup.put("CurNum",1);
                NewGroup.put("Loc", LocEditTxt.getText().toString());
                NewGroup.put("Info", editText.getText().toString());

                // Add relation: group to user
                ParseRelation relation2 = NewGroup.getRelation("MyMembers");
                relation2.add(currentUser);

                NewGroup.saveInBackground();

                // Add relation: user to group
                ParseRelation relation = currentUser.getRelation("MyGroups");
                relation.add(NewGroup);

                currentUser.saveInBackground();

                //finish();
            }
        });
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}