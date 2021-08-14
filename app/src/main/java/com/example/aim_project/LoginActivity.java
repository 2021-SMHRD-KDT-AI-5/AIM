package com.example.aim_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText tv_id,tv_pw;  // 로그인 화면 아이디,비밀번호
    Button btn_login; // 로그인 화면 가입완료 버튼
    TextView tx_join; // 로그인 화면 아이디 없을 시 회원가입
    String[] list = new String[3];

    RequestQueue requestQueue;
    StringRequest stringRequest_login; // 로그인 알고리즘

    CheckBox ck_remember_id2,ck_autologin2;
    Boolean check3 = false;     // 아이디    기억용 boolean
    Boolean check4 = false;     // 자동로그인 기억용 boolean

    DBManager manager; // 로그인을 위한 DBManager 객체 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_id = findViewById(R.id.tv_id);
        tv_pw = findViewById(R.id.tv_pw);
        btn_login = findViewById(R.id.btn_login);
        tx_join = findViewById(R.id.tx_join);
        ck_remember_id2 = findViewById(R.id.ck_remember_id2);
        ck_autologin2 = findViewById(R.id.ck_autologin2);

        // requestQueue 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        manager = new DBManager(getApplicationContext()); // 로그인을 위한 DBManager 객체 생성

        boolean okman = manager.loginOpCheck();

        if (okman == true) { // 로그인 옵션 테이블이 생성되어 있을경우
            list = manager.loginOp_return();

            if (list[2].equals("yeah")) {  // 자동로그인 체크한경우
                Toast.makeText(getApplicationContext(), list[0] + "님, 환영합니다!", Toast.LENGTH_SHORT).show();
                ck_autologin2.setChecked(true);
                check4 = true;
                Intent it_login2 = new Intent(LoginActivity.this, MainActivity.class);
                // 나중에 로그인한 회원 정보 담아서 보낼것!!!
                it_login2.putExtra("loginId", list[0]);
                startActivity(it_login2);

            } else if (list[1].equals("yeah")) {    // 아이디 기억 체크한경우
                ck_remember_id2.setChecked(true);
                check3 = true;
                tv_id.setText(list[0]);
            }
        }else{      // 어플 사용 처음일 경우 default 값으로 넣어주기
            manager.loginOpDefault();
        }



            stringRequest_login = new StringRequest(Request.Method.POST, "http://172.30.1.1:8086/AIM_DBServer/LoginServlet",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // 응답을 처리
                            if (response.equals("true")) {
                                Toast.makeText(getApplicationContext(), tv_id.getText().toString() + "님, 환영합니다!", Toast.LENGTH_SHORT).show();
                                Intent it_login = new Intent(LoginActivity.this, MainActivity.class);
                                // 나중에 로그인한 회원 정보 담아서 보낼것!!!
                                it_login.putExtra("loginId", tv_id.getText().toString());
                                startActivity(it_login);
                            } else {
                                Toast.makeText(getApplicationContext(), "아이디와 비번을 확인해보셔요 아니면 서버", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("id", tv_id.getText().toString());
                    params.put("pw", tv_pw.getText().toString());

                    return params;
                }
            };

            // 회원가입 텍스트 누르면 -> 회원가입 화면으로
            tx_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it_join = new Intent(LoginActivity.this, JoinActivity.class);
                    startActivityForResult(it_join, 1001);
                }
            });


            // 로그인이 완료되면 -> 메인화면으로
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    requestQueue.add(stringRequest_login);
                }
            });

        ck_remember_id2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check3 == true){     // 아이디 기억 해제 하기
                    manager.idRember_off();
                    check3 = false;
                }else{                  // 아이디 기억 설정 하기
                    manager.idRember_on();
                    check3 = true;
                }
            }
        });

        ck_autologin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check4 == true){     // 자동로그인 해제 하기
                    manager.autoLogin_off();
                    check4 = false;
                }else{                  // 자동로그인 설정 하기
                    manager.autoLogin_on();
                    check4 = true;
                }
            }
        });


        }

    }

