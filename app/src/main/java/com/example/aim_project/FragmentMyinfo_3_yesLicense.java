package com.example.aim_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FragmentMyinfo_3_yesLicense extends Fragment {

    TextView txt_licenseInfo, txt_availDay, txt_useDay, txt_LicenseType;
    Button btn_delete;
    RequestQueue requestQueue;
    StringRequest StringRequest_selectLicense;
    StringRequest StringRequest_deleteLicense;
    FragmentMyinfo_3_bye fragmentMyinfo_3_bye;
    String[] str = new String[3];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo_3_yes_license, container, false);
        ;

        txt_licenseInfo = view.findViewById(R.id.txt_licenseInfo);
        txt_availDay = view.findViewById(R.id.txt_availDay);
        txt_useDay = view.findViewById(R.id.txt_useDay);
        txt_LicenseType = view.findViewById(R.id.txt_LicenseType);
        btn_delete = view.findViewById(R.id.btn_delete);
        fragmentMyinfo_3_bye = new FragmentMyinfo_3_bye();


        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");

        // 현재시간
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time1 = format1.format(time);
        String time2 = time1.substring(0, 10);
        String[] time_new = time2.split("-");
        //String[] time_new = {"2021","11","2"};


        /////----------------------- 정액권 조회 -----------------------/////

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest_selectLicense = new StringRequest(Request.Method.POST, "http://172.30.1.1:8086/AIM_DBServer/LicenseServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        str = response.split(",");

                        String time3 = str[2].substring(0, 10);
                        String[] time_old = time3.split("-");

                        int[] time_old_ = new int[3];
                        int[] time_new_ = new int[3];
                        int day = 0;    // 카운트

                        for (int i = 0; i < 3; i++) {       // 비교를 위한 int배열 생성
                            time_old_[i] = Integer.parseInt(time_old[i]);
                            time_new_[i] = Integer.parseInt(time_new[i]);
                        }

                        if (time_new_[0] != time_old_[0]) {
                            day += (time_new_[0] > time_old_[0]) ? (time_new_[0] - time_old_[0]) * 365 : (time_old_[0] - time_new_[0]) * -365;
                        }
                        if (time_new_[1] != time_old_[1]) {
                            day += (time_new_[1] > time_old_[1]) ? (time_new_[1] - time_old_[1]) * 30 : (time_old_[1] - time_new_[1]) * -30;
                        }
                        if (time_new_[2] != time_old_[2]) {
                            day += (time_new_[2] > time_old_[2]) ? 1 : -1;
                        }
                        int cnt = 0;
                        if (str[0].equals("01")) {
                            cnt = 30;
                        } else if (str[0].equals("06")) {
                            cnt = 180;
                        } else if (str[0].equals("12")) {
                            cnt = 365;
                        }

                        txt_availDay.setText(cnt - day + "일");
                        txt_licenseInfo.setText(u_id + "님 의 정액권 정보");
                        txt_useDay.setText(str[2] + "");

                        if (str[1].equals("yeah")) {
                            if (str[0].equals("01")) {
                                txt_LicenseType.setText("1개월 정액권(쿠폰)");
                                btn_delete.setText("해지");
                            } else if (str[0].equals("06")) {
                                txt_LicenseType.setText("6개월 정액권(쿠폰)");
                                btn_delete.setText("해지");
                            } else if (str[0].equals("12")) {
                                txt_LicenseType.setText("12개월 정액권(쿠폰)");
                                btn_delete.setText("해지");
                            }
                        } else {
                            if (str[0].equals("01")) {
                                txt_LicenseType.setText("1개월 정액권");
                                btn_delete.setText("환불");
                            } else if (str[0].equals("06")) {
                                txt_LicenseType.setText("6개월 정액권");
                                btn_delete.setText("환불");
                            } else if (str[0].equals("12")) {
                                txt_LicenseType.setText("12개월 정액권");
                                btn_delete.setText("환불");
                            }
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

                params.put("id", u_id);
                params.put("where", "select");

                return params;
            }
        };

        requestQueue.add(StringRequest_selectLicense);



        StringRequest_deleteLicense = new StringRequest(Request.Method.POST, "http://172.30.1.1:8086/AIM_DBServer/LicenseServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답을 처리
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_3_bye).commit();
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

                params.put("id", u_id);  //license_type   codeOrNot
                params.put("where","delete");

                return params;
            }
        };


        /////----------------------- 클릭이벤트 -----------------------/////

        btn_delete.setOnClickListener(new View.OnClickListener() {      // 환불 or 취소버튼 (나중에 다이얼로그로 한번더 확인 ㄱ)
            @Override
            public void onClick(View v) {
                requestQueue.add(StringRequest_deleteLicense);
            }
        });


        return view;
    }
}