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
    View rootview;

    // Declare Variables
    ListView listview;
    ArrayAdapter<String> adapter;
    private Button button;





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

 /*   public class RemoteDataTask extends AsyncTask<Void, Void, Void>{
    @Override
            protected void onPreExecute(){
        super.onPreExecute();
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(getActivity());
        // Set progressdialog title
        mProgressDialog.setTitle("Loading Program, Please Wait :)");
        // Set progressdialog message
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();

    }
*/
      //  @Override
     /*   protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("StudyGroups");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> GroupList, ParseException e) {
                    if (e == null) {
                        Log.d("score", "Retrieved " + GroupList.size() + " scores");
                        listview = (ListView) getView().findViewById(R.id.list);
                        adapter = new ArrayAdapter<String>(getActivity(),R.layout.listview);
                        for (ParseObject StudyGroup : GroupList) {
                            adapter.add(StudyGroup.getString("GroupName"));
                        }
                        listview.setAdapter(adapter);
                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });
            try {
                ob = query.find();
                Toast.makeText(getActivity(),"doInBackground is running",Toast.LENGTH_LONG).show();

            } catch (com.parse.ParseException e) {
                Toast.makeText(getActivity(), "Error, " +     e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }*/
       //     return null;
     //   }}
   /* protected void onPostExecute(Void result){

        Toast.makeText(getActivity(),"onPostExecute is running",Toast.LENGTH_LONG).show();

        listview = (ListView) getView().findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.matches_layout);
        for (ParseObject StudyGroups : ob) {
            adapter.add((String) StudyGroups.get("GroupName"));
        }
        listview.setAdapter(adapter);
        mProgressDialog.dismiss();
    }*/



//
//        mainAdapter = new ParseQueryAdapter<ParseObject>(getActivity().getBaseContext(), "StudyGroups");
//        mainAdapter.setTextKey("GroupName");
//
//        listView = (ListView) rootView.findViewById(android.R.id.list);
//        listView.setAdapter(mainAdapter);
//        mainAdapter.loadObjects();


//}



//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        rootview = inflater.inflate(R.layout.matches_layout, container, false);
//
//        // Initialize main ParseQueryAdapter
//        mainAdapter = new ParseQueryAdapter<ParseObject>(getActivity().getBaseContext(), "StudyGroups");
//        mainAdapter.setTextKey("GroupName");
//
//        // Initialize the subclass of ParseQueryAdapter
//        urgentTodosAdapter = new CustomAdapter(getActivity().getBaseContext());
//
//        // Initialize ListView and set initial view to mainAdapter
//        listView = (ListView) getView().findViewById(R.id.list);
//        listView.setAdapter(mainAdapter);
//        mainAdapter.loadObjects();
//
//        // Initialize toggle button
//        Button toggleButton = (Button) getView().findViewById(R.id.toggleButton);
//        toggleButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (listView.getAdapter() == mainAdapter) {
//                    listView.setAdapter(urgentTodosAdapter);
//                    urgentTodosAdapter.loadObjects();
//                } else {
//                    listView.setAdapter(mainAdapter);
//                    mainAdapter.loadObjects();
//                }
//            }
//
//        });
//
//        return rootview;
//    }
//}