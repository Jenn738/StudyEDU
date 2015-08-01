package com.parse.starter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.GetCallback;
/**
 * Created by yiningchen on 7/26/15.
 */
public class GroupDetail_Fragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        String StrGroupID = bundle.getString("GroupID", "DEFAULT");

        //inflate and return the layout
        final View v = inflater.inflate(R.layout.fragment_groupdetail, container, false);
        Button button2;
        button2 = (Button) v.findViewById(R.id.button2);

        // retrieve from parse
        ParseQuery<ParseObject> query = ParseQuery.getQuery("StudyGroups");
        query.getInBackground(StrGroupID, new GetCallback<ParseObject>() {
            public void done(ParseObject TargetGroup, ParseException e) {
                if (e == null) {
                    // TargetGroup will be the result

                    TextView dept,course,spinnerDate,spinnerHour, spinnerMaxNum;
                    TextView LocEditTxt,editText,nameEditTxt,CurNum;
                    dept = (TextView) v.findViewById(R.id.spinner1);
                    course = (TextView) v.findViewById(R.id.spinner2);
                    spinnerDate = (TextView) v.findViewById(R.id.date);
                    spinnerHour = (TextView) v.findViewById(R.id.hour);
                    spinnerMaxNum = (TextView) v.findViewById(R.id.spinnerMaxNum);
                    LocEditTxt = (TextView) v.findViewById(R.id.LocEditTxt);
                    editText = (TextView) v.findViewById(R.id.editText);
                    nameEditTxt = (TextView) v.findViewById(R.id.nameEditTxt);
                    CurNum = (TextView) v.findViewById(R.id.CurNum);
                    dept.setText(TargetGroup.getString("Department"));
                    course.setText(TargetGroup.getString("Class"));
                    spinnerDate.setText(TargetGroup.getString("Month")+"/"+TargetGroup.getString("Day")+"/"+TargetGroup.getString("Year"));
                    spinnerHour.setText(TargetGroup.getString("Hour")+":"+TargetGroup.getString("Minute")+" "+TargetGroup.getString("AMPM"));
                    int u=TargetGroup.getInt("CurNum");
                    CurNum.setText(Integer.toString(u));
                    u=TargetGroup.getInt("MaxNum");
                    spinnerMaxNum.setText(Integer.toString(u));
                    LocEditTxt.setText(TargetGroup.getString("Loc"));
                    editText.setText(TargetGroup.getString("Info"));
                    nameEditTxt.setText(TargetGroup.getString("GroupName"));

                } else {
                    // something went wrong
                    Log.d("group details page ", "Error: " + e.getMessage());
                }
            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TO DO: increment current number of people in group

                Toast.makeText(getActivity(),
                        "Group joined",
                        Toast.LENGTH_SHORT).show();
                // To be finished
            }
        });

        return v;
    }
}
