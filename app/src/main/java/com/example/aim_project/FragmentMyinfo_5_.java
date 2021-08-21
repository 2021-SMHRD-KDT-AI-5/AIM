package com.example.aim_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.Map;

public class FragmentMyinfo_5_ extends Fragment {

    Spinner spinner3;
    TextView txt_logout,txt_delete_bo;
    RequestQueue requestQueue;
    StringRequest StringRequest_deleteBo; // 보험 정보 삭제
    CheckBox ck_remember_id,ck_autologin;
    Boolean check1 = false;
    Boolean check2 = false;
    DBManager manager;
    String[] list = new String[3];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_myinfo_5_, container, false);

        spinner3 = view.findViewById(R.id.spinner3);
        txt_logout = view.findViewById(R.id.txt_logout);
        txt_delete_bo = view.findViewById(R.id.txt_delete_bo);
        ck_remember_id = view.findViewById(R.id.ck_remember_id);
        ck_autologin = view.findViewById(R.id.ck_autologin);
        manager = new DBManager(getActivity().getApplicationContext());


        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");

        boolean okman = manager.loginOpCheck();

        list = manager.loginOp_return();

        if (list[2].equals("yeah")) {  // 자동로그인 체크한경우
            ck_autologin.setChecked(true);
            check2 = true;
        }
        if (list[1].equals("yeah")) {  // 아이디 기억 체크한경우
            ck_remember_id.setChecked(true);
            check1 = true;
        }

        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.test3, android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(monthAdapter);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //txt_1.setText(parent.getItemAtPosition(position)+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest_deleteBo = new StringRequest(Request.Method.POST,"http://172.30.1.15:8090/AIM_DBServer/BoDeleteServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            Toast.makeText(getActivity().getApplicationContext(),"등록하신 보험 정보가 초기화 되었습니다.",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(),"등록된 보험 정보가 없습니다.",Toast.LENGTH_SHORT).show();
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
                params.put("id", u_id);
                return params;
            }
        };


        //////////////////////////////////////////////////////
        //                      클릭이벤트                   //

        txt_logout.setOnClickListener(new View.OnClickListener() {  // 로그아웃 버튼
            @Override
            public void onClick(View v) {
                        Intent go_logout = new Intent(getActivity(), LoginActivity.class);
                        startActivity(go_logout);
                    }
        });

        txt_delete_bo.setOnClickListener(new View.OnClickListener() {  // 보험정보 초기화 버튼
            @Override
            public void onClick(View v) {
                // 나중에 다이얼로그로 초기화 할건지 확인하고 하기
                requestQueue.add(StringRequest_deleteBo);
            }
        });

        ck_remember_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check1 == true){     // 아이디 기억 해제 하기
                    manager.idRember_off();
                    check1 = false;
                }else{                  // 아이디 기억 설정 하기
                    manager.idRember_on();
                    check1 = true;
                }
            }
        });

        ck_autologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check2 == true){     // 자동로그인 해제 하기
                    manager.autoLogin_off();
                    check2 = false;
                }else{                  // 자동로그인 설정 하기
                    manager.autoLogin_on();
                    check2 = true;
                }
            }
        });







        return view;
    }
}