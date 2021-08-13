package com.example.aim_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class FragmentMyinfo_2_noBo extends Fragment {

    FragmentMyinfo_2_addBo fragmentMyinfo_2_addBo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_myinfo_2_no_bo, container, false);

        Button btn_addBo = view.findViewById(R.id.btn_addBo);
        Button btn_intro_kidBo = view.findViewById(R.id.btn_intro_kidBo);
        fragmentMyinfo_2_addBo = new FragmentMyinfo_2_addBo();


        // 보험 등록 페이지
        btn_addBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_2_addBo).commit();

            }
        });

        // 보험사 소개 페이지
        btn_intro_kidBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Introduce = new Intent(Intent.ACTION_VIEW, Uri.parse("https://direct.samsunglife.com/child.eds?cid=di:sli:child:menu/"));
                startActivity(Introduce);

            }
        });

        return view;
    }
}