package com.example.aim_project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

public class FragmentMyinfo_3_pay extends Fragment {

    Spinner spinner4;
    Button btn_buy;
    CheckBox ck1, ck2, ck3;
    RequestQueue requestQueue;
    StringRequest StringRequest_insertLicense;
    FragmentMyinfo_3_finish fragmentMyinfo_3_finish;
    FragmentMyinfo fragmentMyinfo;
    ImageView img_backinfo3;
    String str = "";
    Dialog dilaog01,dilaog02,dilaog03;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo_3_pay, container, false);

        spinner4 = view.findViewById(R.id.spinner4);
        btn_buy = view.findViewById(R.id.btn_buy);
        ck1 = view.findViewById(R.id.ck1);
        ck2 = view.findViewById(R.id.ck2);
        ck3 = view.findViewById(R.id.ck3);
        fragmentMyinfo_3_finish = new FragmentMyinfo_3_finish();
        fragmentMyinfo = new FragmentMyinfo();
        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");
        img_backinfo3 = view.findViewById(R.id.img_backinfo3);

        dilaog01 = new Dialog(getActivity());
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog01.setContentView(R.layout.dialog11_select_li);

        dilaog02 = new Dialog(getActivity());
        dilaog02.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog02.setContentView(R.layout.dialog12_checkop);

        dilaog03 = new Dialog(getActivity());
        dilaog03.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog03.setContentView(R.layout.dialog13_paylicense);

        // 정액권 선택 스피너
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.test4, android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(monthAdapter);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str = parent.getItemAtPosition(position) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 오라클 License DB에 insert
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest_insertLicense = new StringRequest(Request.Method.POST, "http://172.30.1.15:8090/AIM_DBServer/LicenseServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답을 처리
                        if (response.equals("1")) {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragmentMyinfo_3_finish).commit();
                        } else {
                            Toast.makeText(getContext(), "구매 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

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

                String str2 = "";

                if (str.equals("월정액권")) {
                    str2 = "01";
                } else if (str.equals("6개월")) {
                    str2 = "06";
                } else {
                    str2 = "12";
                }

                params.put("id", u_id);  //license_type   codeOrNot
                params.put("license_type", str2);
                params.put("codeOrNot", "nope");
                params.put("where","insert");

                return params;
            }
        };

        /////----------------------- 클릭이벤트 -----------------------/////


        // 구매하기 버튼인데,  결제서비스 구현하는걸 해볼지 말지 고민입니다..
        // 우선은 따로 결제없이 바로 구매되는걸로 되어있음
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str.equals("정액권 선택")) {  // 정액권을 안골랐다면
                    showDialog01();
                } else {       // 정액권 골랐다면

                    if (ck1.isChecked() == true && ck2.isChecked() == true) {     // 약관3개 모두 동의했다면~
                        showDialog03();
                    } else {
                        showDialog02();
                    }
                }

            }
        });


        img_backinfo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragmentMyinfo).commit();
            }
        });


        return view;
    }


    // dialog01을 디자인하는 함수
    public void showDialog01() {
        dilaog01.show();

        dilaog01.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog01.dismiss();          // 다이얼로그 닫기
            }
        });
    }

    public void showDialog02(){
        dilaog02.show();

        dilaog02.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilaog02.dismiss();          // 다이얼로그 닫기
            }
        });
    }

    public void showDialog03(){
        dilaog03.show();

        dilaog03.findViewById(R.id.btn_nope).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilaog03.dismiss();          // 다이얼로그 닫기
            }
        });

        dilaog03.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                requestQueue.add(StringRequest_insertLicense);
                dilaog03.dismiss();

            }
        });
    }

}