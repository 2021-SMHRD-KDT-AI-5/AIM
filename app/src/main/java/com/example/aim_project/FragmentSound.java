package com.example.aim_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentSound extends Fragment {
    FragmentSound fragmentSound;
    FragmentMain fragmentMain;
    ListView listView;
    ArrayList<String> arlam_test = new ArrayList<>();
    DBHelper dbHelper;
    DBManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentSound = new FragmentSound();
        fragmentMain = new FragmentMain();
        View view  = inflater.inflate(R.layout.fragment_sound, container, false);
        listView = view.findViewById(R.id.listview);
        ImageView img_logo = view.findViewById(R.id.img_logo);
//        manager = new DBManager(getContext());
        manager = new DBManager(getActivity().getApplicationContext());

//        arlam_test.add("테스트");
        String log = "";
        for(int i =0; i < manager.arlam_select_info().size();i++){
            for(int j = 0; j < 4; j++){
                String test1 =manager.arlam_select_info().get(i)[j] +"  ";
                log += test1;
            }
            arlam_test.add(log);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.simplelist,arlam_test);
        listView.setAdapter(adapter);

        img_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container2,fragmentMain).commit();


            }
        });

        // 알람로그 테이블 테스트

        String list = manager.arlam_select();

//        String baby_name = manager.baby_name_select(list);
        String a = "";
        manager.arlam(list,a,getTime(),"cry");

        // Inflate the layout for this fragment
        return view;

    }
    // delete please
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = dateFormat.format(date);
        return getTime; }
}