package com.parse.starter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

/**
 * Created by nanhu on 7/15/15.
 */

public class MyOnItemSelectedListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView parent, View view, int pos, long id) {
            Toast.makeText(parent.getContext(), "Selected: " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView parent) {

        }
    }

