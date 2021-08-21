package com.example.aim_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnv;

    FragmentCamera fragmentCamera;
//    FragmentDiary fragmentDiary;
    FragmentDiary_1 fragmentDiary_1;
    FragmentMyinfo fragmentMyinfo;
    FragmentSound fragmentSound;
    FragmentMain fragmentMain;
    FragmentMyinfo_1_ fragmentMyinfo_1_;
    FragmentMyinfo_2_noBo fragmentMyinfo_2_noBo_;
    FragmentMyinfo_3_noLicense fragmentMyinfo_3_noLicense_;
    FragmentMyinfo_4_ fragmentMyinfo_4_;
    FragmentMyinfo_5_ fragmentMyinfo_5_;
    FragmentMyinfo_6_ fragmentMyinfo_6_;

//    private String TAG; //이 부분 수정
private static final String TAG = "FMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnv = findViewById(R.id.menubar);
        fragmentCamera = new FragmentCamera();
//        fragmentDiary = new FragmentDiary();
        fragmentDiary_1 = new FragmentDiary_1();
        fragmentMyinfo = new FragmentMyinfo();
        fragmentSound = new FragmentSound();
        fragmentMain = new FragmentMain();
        fragmentMyinfo_1_ = new FragmentMyinfo_1_();
        fragmentMyinfo_2_noBo_ = new FragmentMyinfo_2_noBo();
        fragmentMyinfo_3_noLicense_ = new FragmentMyinfo_3_noLicense();
        fragmentMyinfo_4_ = new FragmentMyinfo_4_();
        fragmentMyinfo_5_ = new FragmentMyinfo_5_();
        fragmentMyinfo_6_ = new FragmentMyinfo_6_();

        FirebaseMessaging.getInstance().getToken().
                addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete( Task<String> task) {
                if (!task.isSuccessful()){
                    Log.w(TAG,"Fetching FCM registration failed",task.getException());
                    return;
                }

                String token = task.getResult();

                String msg = getString(R.string.msg_token_fmt,token);
                Log.d(TAG,msg);
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();

            }
        });

//        if(getIntent().getExtras() != null){
//            Log.d("MainActivity",getIntent().getExtras().getString("test"));
//        }


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container2,fragmentMain).commit();


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.uq:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container2,fragmentCamera).commit();
                        break;
                    case R.id.diary1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container2,fragmentDiary_1).commit();
                        break;
                    case R.id.chatbot:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container2,fragmentMyinfo).commit();
                        break;
                    case R.id.oneQue:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container2,fragmentSound).commit();

                    break;
//                테스트
                }
                return true;
            }
        });
    }

}