package com.example.aim_project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    FragmentMyinfo fragmentMyinfo;
    String[] str = new String[3];
    Dialog dilaog01,dilaog02;
    String code ="n";
    ImageView img_backinfo5;

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
        img_backinfo5 = view.findViewById(R.id.img_backinfo5);
        fragmentMyinfo = new FragmentMyinfo();

        dilaog01 = new Dialog(getActivity());
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog01.setContentView(R.layout.dialog14_undo);

        dilaog02 = new Dialog(getActivity());
        dilaog02.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog02.setContentView(R.layout.dialog15_undo_code);


        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");

        // ????????????
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time1 = format1.format(time);
        String time2 = time1.substring(0, 10);
        String[] time_new = time2.split("-");
        //String[] time_new = {"2021","11","2"};


        /////----------------------- ????????? ?????? -----------------------/////

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest_selectLicense = new StringRequest(Request.Method.POST, "http://172.30.1.15:8090/AIM_DBServer/LicenseServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        str = response.split(",");

                        String time3 = str[2].substring(0, 10);
                        String[] time_old = time3.split("-");

                        int[] time_old_ = new int[3];
                        int[] time_new_ = new int[3];
                        int day = 0;    // ?????????

                        for (int i = 0; i < 3; i++) {       // ????????? ?????? int?????? ??????
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

                        txt_availDay.setText(cnt - day + "???");
                        txt_licenseInfo.setText(u_id + "??? ??? ????????? ??????");
                        txt_useDay.setText(str[2] + "");

                        if (str[1].equals("yeah")) {
                            if (str[0].equals("01")) {
                                txt_LicenseType.setText("1?????? ?????????(??????)");
                                btn_delete.setText("??????");
                                code = "y";
                            } else if (str[0].equals("06")) {
                                txt_LicenseType.setText("6?????? ?????????(??????)");
                                btn_delete.setText("??????");
                                code = "y";
                            } else if (str[0].equals("12")) {
                                txt_LicenseType.setText("12?????? ?????????(??????)");
                                btn_delete.setText("??????");
                                code = "y";
                            }
                        } else {
                            if (str[0].equals("01")) {
                                txt_LicenseType.setText("1?????? ?????????");
                                btn_delete.setText("??????");
                                code = "n";
                            } else if (str[0].equals("06")) {
                                txt_LicenseType.setText("6?????? ?????????");
                                btn_delete.setText("??????");
                                code = "n";
                            } else if (str[0].equals("12")) {
                                txt_LicenseType.setText("12?????? ?????????");
                                btn_delete.setText("??????");
                                code = "n";
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override //response??? UTF8??? ??????????????? ????????????
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



        StringRequest_deleteLicense = new StringRequest(Request.Method.POST, "http://172.30.1.15:8090/AIM_DBServer/LicenseServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // ????????? ??????
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo_3_bye).commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

            @Override //response??? UTF8??? ??????????????? ????????????
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


        /////----------------------- ??????????????? -----------------------/////

        btn_delete.setOnClickListener(new View.OnClickListener() {      // ?????? or ???????????? (????????? ?????????????????? ????????? ?????? ???)
            @Override
            public void onClick(View v) {
                if(code.equals("y")){
                    showDialog02();
                }else{
                    showDialog01();
                }
            }
        });

        img_backinfo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo).commit();
            }
        });


        return view;
    }

    public void showDialog01(){
        dilaog01.show();

        dilaog01.findViewById(R.id.btn_nope).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilaog01.dismiss();          // ??????????????? ??????
            }
        });

        dilaog01.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ????????? ?????? ??????
                requestQueue.add(StringRequest_deleteLicense);
                dilaog01.dismiss();

            }
        });
    }

    public void showDialog02(){
        dilaog02.show();

        dilaog02.findViewById(R.id.btn_nope).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilaog02.dismiss();          // ??????????????? ??????
            }
        });

        dilaog02.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ????????? ?????? ??????
                requestQueue.add(StringRequest_deleteLicense);
                dilaog02.dismiss();

            }
        });
    }

}