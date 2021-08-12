package com.example.aim_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    FragmentCamera fragmentCamera;
//    FragmentDiary fragmentDiary;
    FragmentDiary_1 fragmentDiary_1;
    FragmentMyinfo fragmentMyinfo;
    FragmentSound fragmentSound;
    FragmentMain fragmentMain;
    FragmentMyinfo_1_ fragmentMyinfo_1_;
    FragmentMyinfo_2_ fragmentMyinfo_2_;
    FragmentMyinfo_3_ fragmentMyinfo_3_;
    FragmentMyinfo_4_ fragmentMyinfo_4_;
    FragmentMyinfo_5_ fragmentMyinfo_5_;
    FragmentMyinfo_6_ fragmentMyinfo_6_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnv = findViewById(R.id.bottonbar);
        fragmentCamera = new FragmentCamera();
//        fragmentDiary = new FragmentDiary();
        fragmentDiary_1 = new FragmentDiary_1();
        fragmentMyinfo = new FragmentMyinfo();
        fragmentSound = new FragmentSound();
        fragmentMain = new FragmentMain();
        fragmentMyinfo_1_ = new FragmentMyinfo_1_();
        fragmentMyinfo_2_ = new FragmentMyinfo_2_();
        fragmentMyinfo_3_ = new FragmentMyinfo_3_();
        fragmentMyinfo_4_ = new FragmentMyinfo_4_();
        fragmentMyinfo_5_ = new FragmentMyinfo_5_();
        fragmentMyinfo_6_ = new FragmentMyinfo_6_();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragmentMain).commit();


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.check:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container,fragmentCamera).commit();
                        break;
                    case R.id.diary:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container,fragmentDiary_1).commit();
                        break;
                    case R.id.myinfo:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container,fragmentMyinfo).commit();
                        break;
                    case R.id.sound:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container,fragmentSound).commit();

                    break;

                }
                return true;
            }
        });
    }
}