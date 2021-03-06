package com.example.aim_project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class FragmentMyinfo extends Fragment {

    FragmentMyinfo fragmentMyinfo;
    FragmentMyinfo_1_ fragmentMyinfo_1_;
    FragmentMyinfo_2_noBo fragmentMyinfo_2_noBo_;
    FragmentMyinfo_3_noLicense fragmentMyinfo_3_noLicense_;
    FragmentMyinfo_3_yesLicense fragmentMyinfo_3_yesLicense;
    FragmentMyinfo_4_ fragmentMyinfo_4_;
    FragmentMyinfo_5_ fragmentMyinfo_5_;
    FragmentMyinfo_6_ fragmentMyinfo_6_;
    FragmentMain fragmentMain;
    FragmentMyinfo_2_ifBo fragmentMyinfo_2_ifBo;
    TextView btn_1,btn_2,btn_3,btn_4,btn_5,btn_6;
    ImageView img1,img_aimlogo_m,img_back_info;

    RequestQueue requestQueue;
    StringRequest StringRequest_selectBo; // 정보 조회
    StringRequest StringRequest_LicenseCheck;

    Dialog dilaog01;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMyinfo = new FragmentMyinfo();
        fragmentMain = new FragmentMain();
        fragmentMyinfo_1_ = new FragmentMyinfo_1_();
        fragmentMyinfo_2_noBo_ = new FragmentMyinfo_2_noBo();
        fragmentMyinfo_3_noLicense_ = new FragmentMyinfo_3_noLicense();
        fragmentMyinfo_3_yesLicense = new FragmentMyinfo_3_yesLicense();
        fragmentMyinfo_4_ = new FragmentMyinfo_4_();
        fragmentMyinfo_5_ = new FragmentMyinfo_5_();
        fragmentMyinfo_6_ = new FragmentMyinfo_6_();
        fragmentMyinfo_2_ifBo = new FragmentMyinfo_2_ifBo();

        View view = inflater.inflate(R.layout.fragment_myinfo, container, false);
        btn_1 = view.findViewById(R.id.btn_1);
        btn_2 = view.findViewById(R.id.btn_2);
        btn_3 = view.findViewById(R.id.btn_3);
        btn_4 = view.findViewById(R.id.btn_4);
        btn_5 = view.findViewById(R.id.btn_5);
        btn_6 = view.findViewById(R.id.btn_6);

        img1 = view.findViewById(R.id.img1);
        img_aimlogo_m = view.findViewById(R.id.img_aimlogo_m);
        img_back_info = view.findViewById(R.id.img_back_info);

        dilaog01 = new Dialog(getActivity());
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog01.setContentView(R.layout.dialog04_no_yes);

        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");



        // requestQueue 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest_selectBo = new StringRequest(Request.Method.POST,"http://172.30.1.15:8090/AIM_DBServer/BoSelectServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답을 처리
                        if(response.equals("true")){      // 보험등록 했었으면 ifBo로
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragmentMyinfo_2_ifBo).commit();
                        }else{                          // 안했으면 noBo로
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragmentMyinfo_2_noBo_).commit();
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

        StringRequest_LicenseCheck = new StringRequest(Request.Method.POST,"http://172.30.1.15:8090/AIM_DBServer/LicenseServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답을 처리
                        if(response.equals("true")){      // 라이센스 있으면 yesLicense로
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragmentMyinfo_3_yesLicense).commit();
                        }else{                          // 없으면 noLicense로
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragmentMyinfo_3_noLicense_).commit();
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
                params.put("where","check");
                return params;
            }
        };


        btn_1.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo_1_).commit();
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                requestQueue.add(StringRequest_selectBo);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                requestQueue.add(StringRequest_LicenseCheck);
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo_4_).commit();
                showDialog01();
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo_5_).commit();
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo_6_).commit();
            }
        });

        img_aimlogo_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMain).commit();
            }
        });

        img_back_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMain).commit();
            }
        });



        return view;
    }

    // dialog01을 디자인하는 함수
    public void showDialog01() {
        dilaog01.show();

        TextView txt_chatgo;
        txt_chatgo = dilaog01.findViewById(R.id.txt_chatgo);
        txt_chatgo.setText("AIM 카카오톡 공식채널로 이동");


        //취소
        dilaog01.findViewById(R.id.btn_nope).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilaog01.dismiss();
            }
        });


        //확인
        dilaog01.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Introduce = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pf.kakao.com/_xhbkks"));
                startActivity(Introduce);
                dilaog01.dismiss();
            }
        });
    }

}