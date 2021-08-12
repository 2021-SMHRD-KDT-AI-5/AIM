package com.example.aim_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {
    ImageView img_back;
    EditText edt_id, edt_pw, edt_email, edt_ip, edt_baby_name, edt_baby_birthday;
    Button join_ok;

    RequestQueue requestQueue; // DB 연결시 필수 생성 객체
    StringRequest stringRequest_join; // 회원가입 알고리즘

//    DBManager manager;

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

//        manager = new DBManager(getApplicationContext()); // 회원가입을 위한 DBManager객체 생성

        // requestQueue 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // stringRequest생성
        // 객체 생성 시 매개변수 4개
        stringRequest_join = new StringRequest(Request.Method.POST, "http://172.30.1.1:8086/AIM_DBServer/JoinServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답을 처리하는 메소드
                        if (response.equals("11")){
                            Toast.makeText(getApplicationContext(),"회원가입 완료! 가입하신 아이디로 로그인해보세요!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"뭔가 잘못됐습니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 에러 감지
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id", edt_id.getText().toString());
                params.put("pw", edt_pw.getText().toString());
                params.put("email", edt_email.getText().toString());
                params.put("ip", edt_ip.getText().toString());
                params.put("babyName", edt_baby_name.getText().toString());
                params.put("babyBirthday", edt_baby_birthday.getText().toString());

                return params;
            }
        };

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                finish();
            }
        });

        // join버튼 눌렀을 때 requestQueue에 StringRequest 전송
        join_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue.add(stringRequest_join);
//                manager.join(id, pw, email, ip); // 회원가입 메소드
//                manager.baby_join(id, babyName, babyBirthday); // 아기 정보 저장 메소드

                Intent it = new Intent();
                finish();

            }
        });

    }
}