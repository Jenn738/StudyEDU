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
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.starter.R;
import android.support.v4.app.ListFragment;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by jenniferjain on 7/8/15.
 */
public class Menu2_Fragment_MyGroupList extends Fragment {

    Button CreateGroupButton;
    View rootview;

    // Declare Variables
    ListView listview;
    ArrayAdapter<String> adapter;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //inflate and return the layout
        View v = inflater.inflate(R.layout.mygrouplist_layout, container, false);


        listview = (ListView) v.findViewById(R.id.list);

        ParseUser user = ParseUser.getCurrentUser();
        ParseRelation relation = user.getRelation("MyGroups");
        ParseQuery query = relation.getQuery();

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> MyGroupList, ParseException e) {
                if (e == null) {
                    Log.d("group", "Retrieved " + MyGroupList.size() + " groups");
                    adapter = new ArrayAdapter<String>(getView().getContext(), R.layout.listview);
                    final List<String> finalList = new ArrayList<String>();
                    for (ParseObject StudyGroup : MyGroupList) {
                        adapter.add(StudyGroup.getString("GroupName") + "\n" + "Meeting Date: " + StudyGroup.getString("Month") +
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

                            Fragment GroupDetail2_Fragment = new GroupDetail2_Fragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            Bundle bundle = new Bundle();
                            bundle.putString("GroupID", finalList.get(itemPosition));
                            GroupDetail2_Fragment.setArguments(bundle);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, GroupDetail2_Fragment)
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
                    Log.d("MyGroupList query ", "Error: " + e.getMessage());
                }
            }
        });

        return v;

    }

}