package com.example.aim_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    RequestQueue requestQueue;
    StringRequest stringRequest_login; // 로그인 알고리즘

//    DBManager manager; // 로그인을 위한 DBManager 객체 생성 맨

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_id = findViewById(R.id.tv_id);
        tv_pw = findViewById(R.id.tv_pw);
        btn_login = findViewById(R.id.btn_login);
        tx_join = findViewById(R.id.tx_join);

        // requestQueue 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());

//        manager = new DBManager(getApplicationContext()); // 로그인을 위한 DBManager 객체 생성

        stringRequest_login = new StringRequest(Request.Method.POST, "http://172.30.1.1:8086/AIM_DBServer/LoginServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답을 처리
                        if(response.equals("true")){
                            Toast.makeText(getApplicationContext(),tv_id.getText().toString()+"님, 환영합니다!",Toast.LENGTH_SHORT).show();
                            Intent it_login = new Intent(LoginActivity.this, MainActivity.class);
                            // 나중에 로그인한 회원 정보 담아서 보낼것!!!
                            it_login.putExtra("loginId", tv_id.getText().toString());
                            startActivity(it_login);
                        }else{
                            Toast.makeText(getApplicationContext(),"아이디와 비번을 확인해보셔요 아니면 서버",Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
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
                Intent it_join = new Intent(LoginActivity.this,JoinActivity.class);
                startActivityForResult(it_join,1001);
            }
        });


        // 로그인이 완료되면 -> 메인화면으로
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestQueue.add(stringRequest_login);

//                if (manager.login(id,pw) == true){ // 로그인 성공시
//                    Toast.makeText(getApplicationContext(),id+"님 환영합니다!",Toast.LENGTH_SHORT).show();
//
//                    Intent it_login = new Intent(LoginActivity.this, MainActivity.class);
//                    // 나중에 로그인한 회원 정보 담아서 보낼것!!!
//                    startActivity(it_login);
//                }else { // 로그인 실패시
//                    Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호가 옳지 않습니다.",Toast.LENGTH_SHORT).show();
//                }


            }
        });






    }
}