package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;



/**
 * Created by jenniferjain on 7/8/15.
 */
public class Menu1_Fragment extends Fragment {
    Button CreateGroupButton;
    View rootview;
    private Button button;
    private Spinner spinner1, spinner2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu1_layout, container, false);
        CreateGroupButton = (Button) rootview.findViewById(R.id.create_study_group);

        button = (Button) rootview.findViewById(R.id.button);

        spinner1 = (Spinner) rootview.findViewById(R.id.spinner1);
        spinner2 = (Spinner) rootview.findViewById(R.id.spinner2);
        spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
        spinner2.setOnItemSelectedListener(new MyOnItemSelectedListener());

        CreateGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Test", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Menu1_Fragment.this.getActivity(), CreateGroup.class);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                        //what to put here
                // Create new fragment and transaction
                Fragment Matches_Fragment = new Matches_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("Dept", String.valueOf(spinner1.getSelectedItem()));
                bundle.putString("Class", String.valueOf(spinner2.getSelectedItem()));
                Matches_Fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, Matches_Fragment)
                        .commit();

//                Toast.makeText(getActivity().getApplicationContext(),
//                        "Result : " +
//                                "\nDepartment : " + String.valueOf(spinner1.getSelectedItem()) +
//                                "\nClass : " + String.valueOf(spinner2.getSelectedItem()),
//                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootview;
    }


}
