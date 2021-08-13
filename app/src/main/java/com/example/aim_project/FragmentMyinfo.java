package com.example.aim_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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


public class FragmentMyinfo extends Fragment {

    FragmentMyinfo fragmentMyinfo;
    FragmentMyinfo_1_ fragmentMyinfo_1_;
    FragmentMyinfo_2_noBo fragmentMyinfo_2_noBo_;
    FragmentMyinfo_3_ fragmentMyinfo_3_;
    FragmentMyinfo_4_ fragmentMyinfo_4_;
    FragmentMyinfo_5_ fragmentMyinfo_5_;
    FragmentMyinfo_6_ fragmentMyinfo_6_;
    FragmentMain fragmentMain;
    FragmentMyinfo_2_ifBo fragmentMyinfo_2_ifBo;
    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6;
    ImageView img1;

    RequestQueue requestQueue;
    StringRequest StringRequest_selectBo; // 정보 조회


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMyinfo = new FragmentMyinfo();
        fragmentMain = new FragmentMain();
        fragmentMyinfo_1_ = new FragmentMyinfo_1_();
        fragmentMyinfo_2_noBo_ = new FragmentMyinfo_2_noBo();
        fragmentMyinfo_3_ = new FragmentMyinfo_3_();
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

        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");

        // requestQueue 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest_selectBo = new StringRequest(Request.Method.POST,"http://172.30.1.1:8086/AIM_DBServer/BoSelectServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답을 처리
                        if(response.equals("true")){      // 보험등록 했었으면 ifBo로
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMyinfo_2_ifBo).commit();
                        }else{                          // 안했으면 noBo로
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMyinfo_2_noBo_).commit();
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

        btn_1.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_1_).commit();
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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_3_).commit();
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_4_).commit();
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_5_).commit();
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {   // FragmentMain 으로는 이동함
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_6_).commit();
            }
        });


        return view;
    }

}