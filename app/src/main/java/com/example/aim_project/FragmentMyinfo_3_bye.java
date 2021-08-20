package com.example.aim_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentMyinfo_3_bye extends Fragment {

    Button btn_gomenu_;
    TextView txt_main_4,txt_main_5;
    FragmentMyinfo_3_noLicense fragmentMyinfo_3_noLicense;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo_3_bye, container, false);

        fragmentMyinfo_3_noLicense = new FragmentMyinfo_3_noLicense();
        btn_gomenu_ = view.findViewById(R.id.btn_gomenu_);
        txt_main_4 = view.findViewById(R.id.txt_main_4);
        txt_main_5 = view.findViewById(R.id.txt_main_5);



        btn_gomenu_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragmentMyinfo_3_noLicense).commit();
            }
        });


        return view;
    }
}