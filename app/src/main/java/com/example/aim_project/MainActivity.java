package com.example.aim_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    FragmentCamera fragmentCamera;
    FragmentDiary fragmentDiary;
    FragmentMyinfo fragmentMyinfo;
    FragmentSound fragmentSound;
    FragmentMain fragmentMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnv = findViewById(R.id.bottonbar);
        fragmentCamera = new FragmentCamera();
        fragmentDiary = new FragmentDiary();
        fragmentMyinfo = new FragmentMyinfo();
        fragmentSound = new FragmentSound();
        fragmentMain = new FragmentMain();

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
                                .replace(R.id.container,fragmentDiary).commit();
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