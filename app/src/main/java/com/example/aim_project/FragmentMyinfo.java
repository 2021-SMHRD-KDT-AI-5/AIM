package com.example.aim_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


public class FragmentMyinfo extends Fragment {

    FragmentMyinfo fragmentMyinfo;
    FragmentMyinfo_1_ fragmentMyinfo_1_;
    FragmentMyinfo_2_ fragmentMyinfo_2_;
    FragmentMyinfo_3_ fragmentMyinfo_3_;
    FragmentMyinfo_4_ fragmentMyinfo_4_;
    FragmentMyinfo_5_ fragmentMyinfo_5_;
    FragmentMyinfo_6_ fragmentMyinfo_6_;
    FragmentMain fragmentMain;
    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6;
    ImageView img1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMyinfo = new FragmentMyinfo();
        fragmentMain = new FragmentMain();
        fragmentMyinfo_1_ = new FragmentMyinfo_1_();
        fragmentMyinfo_2_ = new FragmentMyinfo_2_();
        fragmentMyinfo_3_ = new FragmentMyinfo_3_();
        fragmentMyinfo_4_ = new FragmentMyinfo_4_();
        fragmentMyinfo_5_ = new FragmentMyinfo_5_();
        fragmentMyinfo_6_ = new FragmentMyinfo_6_();

        View view = inflater.inflate(R.layout.fragment_myinfo, container, false);
        btn_1 = view.findViewById(R.id.btn_1);
        btn_2 = view.findViewById(R.id.btn_2);
        btn_3 = view.findViewById(R.id.btn_3);
        btn_4 = view.findViewById(R.id.btn_4);
        btn_5 = view.findViewById(R.id.btn_5);
        btn_6 = view.findViewById(R.id.btn_6);

        img1 = view.findViewById(R.id.img1);

        btn_1.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_1_).commit();
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_2_).commit();
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_3_).commit();
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_4_).commit();
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_5_).commit();
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_6_).commit();
            }
        });


        return view;
    }

}