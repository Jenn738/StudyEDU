package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.FindCallback;
import com.parse.ParseQueryAdapter;
import com.parse.starter.R;
import android.support.v4.app.ListFragment;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by jenniferjain on 7/8/15.
 */
public class Matches_Fragment extends Fragment {

    Button CreateGroupButton;

    // Declare Variables
    ListView listview;
    ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Retrieve two key values
        Bundle bundle = this.getArguments();
        String StrDept = bundle.getString("Dept", "DEFAULT");
        String StrClass = bundle.getString("Class", "DEFAULT");


        //inflate and return the layout
        View v = inflater.inflate(R.layout.matches_layout, container, false);

        //declare reference to createGroupButton
        CreateGroupButton = (Button) v.findViewById(R.id.create_study_group);

        listview = (ListView) v.findViewById(R.id.list);

        CreateGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Test", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Matches_Fragment.this.getActivity(), CreateGroup.class);
                startActivity(intent);

            }
        });




        //       new RemoteDataTask().execute();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("StudyGroups");
        query.whereEqualTo("Department",StrDept);
        query.whereEqualTo("Class", StrClass);
        query.findInBackground(new FindCallback<ParseObject>() {


            public void done(List<ParseObject> GroupList, ParseException e) {
                if (e == null) {
                    Log.d("group", "Retrieved " + GroupList.size() + " groups");
                    // listview = (ListView) getView().findViewById(R.id.list);
                    adapter = new ArrayAdapter<String>(getView().getContext(), R.layout.listview);
                    final List<String> finalList = new ArrayList<String>();
                    for (ParseObject StudyGroup : GroupList) {
                        adapter.add(StudyGroup.getString("GroupName") + " ("+StudyGroup.getInt("CurNum") +"/"+StudyGroup.getInt("MaxNum")+")\n" + "Meeting Date: " + StudyGroup.getString("Month") +
                                "/" + StudyGroup.getString("Day") + "/" + StudyGroup.getString("Year") + "\n" + "Meeting Time: " +
                                StudyGroup.getString("Hour") + ":" + StudyGroup.getString("Minute") + " " +
                                StudyGroup.getString("AMPM"));
                        String objectId = StudyGroup.getObjectId();
                        finalList.add(objectId);
                    }
                    listview.setAdapter(adapter);



                    listview.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int position, long arg3) {
                            int itemPosition = position;

                            // ListView Clicked item value
                            String itemValue = (String) listview.getItemAtPosition(position);

                            Fragment GroupDetail_Fragment = new GroupDetail_Fragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            Bundle bundle = new Bundle();
                            bundle.putString("GroupID", finalList.get(itemPosition));
                            GroupDetail_Fragment.setArguments(bundle);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, GroupDetail_Fragment)
                                            //back button leads to this page
                                    .addToBackStack(null)
                                    .commit();

                            // Show Alert
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                                    .show();

                        }
                    });
                } else {
                    Log.d("grouplist", "Error: " + e.getMessage());
                }
            }
        });


        return v;


    }



}
