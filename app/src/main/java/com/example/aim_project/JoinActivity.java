package com.example.aim_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {
    ImageView img_back;
    EditText edt_id, edt_pw, edt_email, edt_ip, edt_baby_name, edt_baby_birthday;
    Button join_ok;

    DBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        img_back = findViewById(R.id.img_back);
        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);
        edt_email = findViewById(R.id.edt_email);
        edt_ip = findViewById(R.id.edt_ip);
        edt_baby_name = findViewById(R.id.edt_baby_name);
        edt_baby_birthday = findViewById(R.id.edt_baby_birtday);
        join_ok = findViewById(R.id.join_ok);

        manager = new DBManager(getApplicationContext()); // 회원가입을 위한 DBManager객체 생성

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                finish();
            }
        });

        join_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edt_id.getText().toString();
                String pw = edt_pw.getText().toString();
                String email = edt_email.getText().toString();
                String ip = edt_ip.getText().toString();
                String babyName = edt_baby_name.getText().toString();
                String babyBirthday = edt_baby_birthday.getText().toString();

                manager.join(id, pw, email, ip); // 회원가입 메소드
                manager.baby_join(id, babyName, babyBirthday); // 아기 정보 저장 메소드

                Toast.makeText(getApplicationContext(),"회원가입에 성공하셨습니다! 가입하신 아이디로 로그인해보세요.",Toast.LENGTH_LONG).show();
                Intent it = new Intent();
                finish();

            }
        });

    }
}