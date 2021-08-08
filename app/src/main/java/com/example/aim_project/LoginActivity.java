package com.example.aim_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    EditText tv_id,tv_pw;  // 로그인 화면 아이디,비밀번호
    Button btn_login; // 로그인 화면 가입완료 버튼
    TextView tx_join; // 로그인 화면 아이디 없을 시 회원가입

    DBManager manager; // 로그인을 위한 DBManager 객체 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_id = findViewById(R.id.tv_id);
        tv_pw = findViewById(R.id.tv_pw);
        btn_login = findViewById(R.id.btn_login);
        tx_join = findViewById(R.id.tx_join);

        manager = new DBManager(getApplicationContext()); // 로그인을 위한 DBManager 객체 생성

        // 회원가입 텍스트 누르면 -> 회원가입 화면으로
        tx_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_join = new Intent(LoginActivity.this,JoinActivity.class);
                startActivityForResult(it_join,1001);
            }
        });


        // 로그인이 완료되면 -> 메인화면으로
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = tv_id.getText().toString();
                String pw = tv_pw.getText().toString();

                if (manager.login(id,pw) == true){ // 로그인 성공시
                    Toast.makeText(getApplicationContext(),id+"님 환영합니다!",Toast.LENGTH_SHORT).show();

                    Intent it_login = new Intent(LoginActivity.this, MainActivity.class);
                    // 나중에 로그인한 회원 정보 담아서 보낼것!!!
                    startActivity(it_login);
                }else { // 로그인 실패시
                    Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호가 옳지 않습니다.",Toast.LENGTH_SHORT).show();
                }


            }
        });






    }
}