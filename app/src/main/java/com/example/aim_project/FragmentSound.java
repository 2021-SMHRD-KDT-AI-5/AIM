package com.example.aim_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.p2p.WifiP2pManager;
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
    DBHelper helper;
    DBManager manager;
    ImageView img_back_info2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentSound = new FragmentSound();
        fragmentMain = new FragmentMain();
        View view  = inflater.inflate(R.layout.fragment_sound, container, false);
        listView = view.findViewById(R.id.listview);
        ImageView img_logo = view.findViewById(R.id.img_logo);
        manager = new DBManager(getActivity().getApplicationContext());
        helper = new DBHelper(getActivity().getApplicationContext());
        img_back_info2 = view.findViewById(R.id.img_back_info2);

        ArrayList<AlarmVO> data = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        int cnt = 0;
        Cursor cursor = db.rawQuery("select * from alarm_log",null);
        while(cursor.moveToNext()){
                data.add(new AlarmVO(cursor.getString(0),cursor.getString(1),
                                cursor.getString(2),cursor.getString(3))
                        );
        }
        alarmAdapter adapter = new alarmAdapter(getContext(),R.layout.arlarmlist, data);
        listView.setAdapter(adapter);
        img_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container2,fragmentMain).commit();

            }
        });


        img_back_info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragmentMain).commit();
            }
        });

        return view;
    }

}