package com.example.aim_project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentMyinfo_1_ extends Fragment {

    Button change_back,change_ok;
    EditText edt_pw,edt_email,edt_ip,edt_baby_name,edt_baby_birthday;
    TextView edt_id;
    ImageView img_backToMyInfo_1,img_aimlogo_1;
    DBManager manager;
    ArrayList<MemberVO> temp = new ArrayList<>();
    FragmentMyinfo fragmentMyinfo;
    FragmentMyinfo_1_ fragmentMyinfo_1_;

    RequestQueue requestQueue;
    StringRequest StringRequest_selectInfo; // 정보 조회
    StringRequest StringRequest_update; // 정보 수정
    Dialog dilaog01,dilaog02;

    //String user_id = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE).getString("id", "none");
    // key : 데이터 꺼낼 때 사용하는 값 ,    // defValue : 키값에 해당하는 데이터가 없을 때 사용할 기본값*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo_1_, container, false);
        manager = new DBManager(getActivity().getApplicationContext());
        fragmentMyinfo = new FragmentMyinfo();
        fragmentMyinfo_1_ = new FragmentMyinfo_1_();

        edt_id = view.findViewById(R.id.edt_id);
        edt_pw = view.findViewById(R.id.edt_pw);
        edt_email = view.findViewById(R.id.edt_email);
        edt_ip = view.findViewById(R.id.edt_ip);
        edt_baby_name = view.findViewById(R.id.edt_baby_name);
        edt_baby_birthday = view.findViewById(R.id.edt_baby_birthday);
        change_back = view.findViewById(R.id.change_back);
        change_ok = view.findViewById(R.id.change_ok);

        img_backToMyInfo_1 = view.findViewById(R.id.img_backToMyInfo_1);
        img_aimlogo_1 = view.findViewById(R.id.img_aimlogo_1);

        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");

        dilaog01 = new Dialog(getActivity());       // Dialog 초기화
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog01.setContentView(R.layout.dialog04_no_yes);         // xml 레이아웃 파일과 연결

        dilaog02 = new Dialog(getActivity());
        dilaog02.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog02.setContentView(R.layout.dialog05_refactor);

        // requestQueue 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest_selectInfo = new StringRequest(Request.Method.POST,"http://172.30.1.15:8090/AIM_DBServer/SelectServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답을 처리
                            String [] str = response.split(",");
                            edt_id.setText(u_id);
                            edt_pw.setText(str[1]);
                            edt_email.setText(str[2]);
                            edt_ip.setText(str[3]);
                            edt_baby_name.setText(str[4]);
                            edt_baby_birthday.setText(str[5]);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){

            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id", u_id);
                return params;
            }
        };

        // 창 열자마자 기본 select
        requestQueue.add(StringRequest_selectInfo);


        StringRequest_update = new StringRequest(Request.Method.POST,"http://172.30.1.15:8090/AIM_DBServer/UpdateServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답을 처리

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){

            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String pw = edt_pw.getText().toString();
                String u_email = edt_email.getText().toString();
                String u_ip = edt_ip.getText().toString();
                String b_name = edt_baby_name.getText().toString();
                String b_birthday = edt_baby_birthday.getText().toString();

                params.put("id",u_id);
                params.put("pw",pw);
                params.put("email",u_email);
                params.put("ip",u_ip);
                params.put("baby_name",b_name);
                params.put("baby_birthday",b_birthday);

                return params;
            }
        };

        // 정보수정 취소 -> 취소 메세지와 함께 뒤로감
        change_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo).commit();
            }
        });

        // 정보변경 완료 -> 완료 메세지와 함께 뒤로감
        change_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01();
            }
        });

        // 좌측상단 뒤로가기 버튼
        img_backToMyInfo_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo).commit();
            }
        });



        return view;
    }

    // dialog01을 디자인하는 함수
    public void showDialog01() {
        dilaog01.show();

        dilaog01.findViewById(R.id.btn_nope).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilaog01.dismiss();          // 다이얼로그 닫기
            }
        });

        dilaog01.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                requestQueue.add(StringRequest_update);
                dilaog01.dismiss();
                showDialog02();
            }
        });
    }


    public void showDialog02(){
        dilaog02.show();

        dilaog02.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo).commit();
                dilaog02.dismiss();
            }
        });
    }

}