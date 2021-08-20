package com.example.aim_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class JoinActivity extends AppCompatActivity {
    ImageView img_back;
    EditText edt_id, edt_pw, edt_email, edt_ip, edt_baby_name, edt_baby_birthday;
    Button join_ok;
    Dialog dilaog01,dilaog02;

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


        dilaog01 = new Dialog(JoinActivity.this);       // Dialog 초기화
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog01.setContentView(R.layout.dialog01_suc);             // xml 레이아웃 파일과 연결

        dilaog02 = new Dialog(JoinActivity.this);
        dilaog02.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog02.setContentView(R.layout.dialog02_fail);


//        manager = new DBManager(getApplicationContext()); // 회원가입을 위한 DBManager객체 생성

        // requestQueue 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        img_back.setOnClickListener(new View.OnClickListener() {    // 회원가입 취소
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                finish();
            }
        });


        //==================================================================================================
        //JSP 전달방식
        join_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String joinMember;
                    String joinBaby;
                    String id = edt_id.getText().toString();
                    String pw = edt_pw.getText().toString();
                    String email = edt_email.getText().toString();
                    String ip = edt_ip.getText().toString();
                    String babyName = edt_baby_name.getText().toString();
                    String babyBirthday = edt_baby_birthday.getText().toString();

                    // 회원가입 정보를 담아 Task로 전달
                    joinMember = new Task("joinMember").execute("joinMember", id, pw, email, ip).get();

                    // 아기등록 정보를 담아 Task에 전달
                    joinBaby = new Task("joinBaby").execute("joinBaby", id, babyName, babyBirthday).get();

                    joinMember = Html.fromHtml(joinMember).toString().trim(); // html태그 제거 및 공백 제거
                    joinBaby = Html.fromHtml(joinBaby).toString().trim(); // html태그 제거 및 공백 제거

                    if(joinMember.equals("1") && joinBaby.equals("1")){
                        showDialog01(); // 아래 showDialog01() 함수 호출
                    }else{
                        showDialog02(); // 아래 showDialog01() 함수 호출
                    }

                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }

            }


        });





        //==================================================================================================
////        ==================================================================================================
////         서블릿 연계 전달방식
//        // stringRequest생성
//        // 객체 생성 시 매개변수 4개
//        stringRequest_join = new StringRequest(Request.Method.POST, "http://172.30.1.1:8086/AIM_DBServer/JoinServlet",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // 응답을 처리하는 메소드
//                        if (response.equals("11")){
//                            Toast.makeText(getApplicationContext(),"회원가입 완료! 가입하신 아이디로 로그인해보세요!",Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(getApplicationContext(),"뭔가 잘못됐습니다",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // 에러 감지
//            }
//        }){
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("id", edt_id.getText().toString());
//                params.put("pw", edt_pw.getText().toString());
//                params.put("email", edt_email.getText().toString());
//                params.put("ip", edt_ip.getText().toString());
//                params.put("babyName", edt_baby_name.getText().toString());
//                params.put("babyBirthday", edt_baby_birthday.getText().toString());
//
//                return params;
//            }
//        };
//
//
//        // join버튼 눌렀을 때 requestQueue에 StringRequest 전송
//        join_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestQueue.add(stringRequest_join);
////                manager.join(id, pw, email, ip); // 회원가입 메소드
////                manager.baby_join(id, babyName, babyBirthday); // 아기 정보 저장 메소드
//
//                Intent it = new Intent();
//                finish();
//
//            }
//        });
////        ==================================================================================================


    }

    // dialog01을 디자인하는 함수
    public void showDialog01() {
        dilaog01.show(); // 다이얼로그 띄우기

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

/*        // 아니오 버튼
        Button noBtn = dilaog01.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dilaog01.dismiss(); // 다이얼로그 닫기
            }
        });*/
        // 네 버튼
        dilaog01.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                Intent it = new Intent();
                finish();           // 앱 종료
            }
        });
    }



    // dialog02을 디자인하는 함수
    public void showDialog02() {
        dilaog02.show();

        dilaog02.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dilaog02.dismiss();          // 다이얼로그 닫기
            }
        });
    }


}