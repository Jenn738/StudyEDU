package com.parse.starter;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.starter.R;
import android.support.v4.app.ListFragment;


import java.util.List;

/**
 * Created by jenniferjain on 7/8/15.
 */
public class Matches_Fragment extends Fragment {

    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate and return the layout
        View v = inflater.inflate(R.layout.matches_layout, container, false);
        new RemoteDataTask().execute();

        return v;

    }

    public class RemoteDataTask extends AsyncTask<Void, Void, Void>{
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

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("StudyGroups");
            try {
                ob = query.find();
                Toast.makeText(getActivity(),"doInBackground is running",Toast.LENGTH_LONG).show();

            } catch (com.parse.ParseException e) {
                Toast.makeText(getActivity(), "Error, " +     e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            return null;
        }}
    protected void onPostExecute(Void result){

        Toast.makeText(getActivity(),"onPostExecute is running",Toast.LENGTH_LONG).show();

        listview = (ListView) getView().findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.matches_layout);
        for (ParseObject StudyGroups : ob) {
            adapter.add((String) StudyGroups.get("GroupName"));
        }
        listview.setAdapter(adapter);
        mProgressDialog.dismiss();
    }



//
//        mainAdapter = new ParseQueryAdapter<ParseObject>(getActivity().getBaseContext(), "StudyGroups");
//        mainAdapter.setTextKey("GroupName");
//
//        listView = (ListView) rootView.findViewById(android.R.id.list);
//        listView.setAdapter(mainAdapter);
//        mainAdapter.loadObjects();


}



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