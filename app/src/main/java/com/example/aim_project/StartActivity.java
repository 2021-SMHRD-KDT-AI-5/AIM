package com.example.aim_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    ImageView img_logo_s;
    TextView txt_anim_msg;

    // 권한 부여 관련 변수
    int nCurrentPermission = 0;
    static final int PERMISSIONS_REQUEST = 0x0000001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        img_logo_s = findViewById(R.id.img_logo_s);
        txt_anim_msg = findViewById(R.id.txt_anim_msg);

        OnCheckPermission();

//////--------------------- 애니메이션 구간 ---------------------------/////


        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.combine_start);   // res - anim 에 있는 애니메이션 xml이름
        Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);   // res - anim 에 있는 애니메이션 xml이름




        Animation.AnimationListener aniListener01 = new Animation.AnimationListener() { // 로고
            public void onAnimationEnd(Animation animation) { // 애니메이션이 끝났을 때

            }

            @Override
            public void onAnimationRepeat(Animation animation) { }      // 반복할때
            @Override
            public void onAnimationStart(Animation animation) {

            }
        };

        Animation.AnimationListener aniListener02 = new Animation.AnimationListener() { // 로고 밑에 문구
            public void onAnimationEnd(Animation animation) { // 애니메이션이 끝났을 때
                Intent To_login = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(To_login);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }      // 반복할때
            @Override
            public void onAnimationStart(Animation animation) {

            }
        };

        anim.setAnimationListener(aniListener01);       // 리스너에 애니메이션 등록
        anim2.setAnimationListener(aniListener02);       // 리스너에 애니메이션 등록

        txt_anim_msg.startAnimation(anim2);
        img_logo_s.startAnimation(anim); // 애니메이션 실행

/////----------------------------------------------------------------/////
    }

    // 권한 부여 메소드(외부 저장소 접근 허용)
    public void OnCheckPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(this,"앱 실행을 위해서는 권한을 설정해야 합니다.",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST);
            }else{
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST);
            }
        }
    }

    // 권한 허용 후 다시 액티비티로 돌아오기
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSIONS_REQUEST:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "앱 실행을 위한 권한이 설정되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "앱 실행을 위한 권한이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}