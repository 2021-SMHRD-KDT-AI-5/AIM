package com.example.aim_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentMyinfo_3_finish extends Fragment {

    Button btn_gomenu;
    FragmentMyinfo_3_yesLicense fragmentMyinfo_3_yesLicense;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo_3_finish, container, false);

        btn_gomenu = view.findViewById(R.id.btn_gomenu);
        fragmentMyinfo_3_yesLicense = new FragmentMyinfo_3_yesLicense();


        btn_gomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_3_yesLicense).commit();
            }
        });


        return view;
    }
}