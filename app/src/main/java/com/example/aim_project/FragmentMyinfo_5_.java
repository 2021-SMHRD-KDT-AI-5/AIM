package com.example.aim_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FragmentMyinfo_5_ extends Fragment {

    Spinner spinner3;
    TextView txt_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_myinfo_5_, container, false);

        spinner3 = view.findViewById(R.id.spinner3);
        txt_logout = view.findViewById(R.id.txt_logout);


        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.test3, android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(monthAdapter);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //txt_1.setText(parent.getItemAtPosition(position)+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        //////////////////////////////////////////////////////
        //                      클릭이벤트                   //

        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent go_logout = new Intent(getActivity(), LoginActivity.class);
                        startActivity(go_logout);
                    }
        });




        return view;
    }
}