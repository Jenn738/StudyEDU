package com.parse.starter;

/**
 * Created by jenniferjain on 7/21/15.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;


public class CustomAdapter extends ParseQueryAdapter<ParseObject> {

    public CustomAdapter(Context context) {
        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("StudyGroups");

//                query.whereEqualTo("Department", null);
//                query.whereEqualTo("Class", null);
                return query;
            }
        });
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.urgent_item, null);
        }

        super.getItemView(object, v, parent);

//		// Add and download the image
//		ParseImageView todoImage = (ParseImageView) v.findViewById(R.id.icon);
//		ParseFile imageFile = object.getParseFile("image");
//		if (imageFile != null) {
//			todoImage.setParseFile(imageFile);
//			todoImage.loadInBackground();
//		}

        // Add the dept view
        TextView deptTextView = (TextView) v.findViewById(R.id.dept);
        deptTextView.setText(object.getString("Department"));

        // Add the class view
        TextView courseTextView = (TextView) v.findViewById(R.id.course);
        courseTextView.setText(object.getString("course"));

        // Add a reminder of how long this item has been outstanding
        TextView timestampView = (TextView) v.findViewById(R.id.timestamp);
        timestampView.setText(object.getCreatedAt().toString());
        return v;
    }

}
