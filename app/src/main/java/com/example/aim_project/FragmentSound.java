package com.example.aim_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class FragmentSound extends Fragment {
    FragmentSound fragmentSound;
    FragmentMain fragmentMain;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSound = new FragmentSound();
        fragmentMain = new FragmentMain();
        View view  = inflater.inflate(R.layout.fragment_sound, container, false);
        ImageView img_logo = view.findViewById(R.id.img_logo);

        img_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container2,fragmentMain).commit();


            }
        });

        // Inflate the layout for this fragment
        return view;

    }
}