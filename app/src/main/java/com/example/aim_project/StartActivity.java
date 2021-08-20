package com.example.aim_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    ImageView img_logo_s;
    TextView txt_anim_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        img_logo_s = findViewById(R.id.img_logo_s);
        txt_anim_msg = findViewById(R.id.txt_anim_msg);



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
}