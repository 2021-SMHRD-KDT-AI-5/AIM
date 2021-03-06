package com.example.aim_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class FragmentMyinfo_3_noLicense extends Fragment {

    Button btn_buyLicense,btn_enterCode;
    ImageView img_backinfo;

    FragmentMyinfo_3_enterCode fragmentMyinfo_3_enterCode;
    FragmentMyinfo_3_pay fragmentMyinfo_3_pay;
    FragmentMyinfo fragmentMyinfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo_3_, container, false);

        btn_buyLicense = view.findViewById(R.id.btn_buyLicense);
        btn_enterCode = view.findViewById(R.id.btn_enterCode);
        fragmentMyinfo_3_enterCode = new FragmentMyinfo_3_enterCode();
        fragmentMyinfo_3_pay = new FragmentMyinfo_3_pay();
        img_backinfo = view.findViewById(R.id.img_backinfo);
        fragmentMyinfo = new FragmentMyinfo();



        btn_enterCode.setOnClickListener(new View.OnClickListener() {       // 코드입력으로 go
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo_3_enterCode).commit();
            }
        });

        btn_buyLicense.setOnClickListener(new View.OnClickListener() {      // 결제창으로 go
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo_3_pay).commit();
            }
        });

        img_backinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo).commit();
            }
        });

        return view;

    }

}
