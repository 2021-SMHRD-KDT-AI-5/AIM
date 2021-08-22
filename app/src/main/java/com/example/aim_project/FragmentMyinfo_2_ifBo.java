package com.example.aim_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;
import java.util.Map;

public class FragmentMyinfo_2_ifBo extends Fragment {

    TextView txt_b, txt_bs, txt_uname, txt_url1, txt_url2, txt_url3;
    RequestQueue requestQueue;
    StringRequest StringRequest_selectBo; // 정보 조회
    ImageView img_backinfo;
    FragmentMyinfo fragmentMyinfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo_2_if_bo, container, false);
        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");   // 로그인한 id

        txt_b = view.findViewById(R.id.txt_b);
        txt_bs = view.findViewById(R.id.txt_bs);
        txt_uname = view.findViewById(R.id.txt_uname);
        txt_url1 = view.findViewById(R.id.txt_url1);
        txt_url2 = view.findViewById(R.id.txt_url2);
        txt_url3 = view.findViewById(R.id.txt_url3);
        img_backinfo = view.findViewById(R.id.img_backinfo);
        fragmentMyinfo = new FragmentMyinfo();

        // requestQueue 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest_selectBo = new StringRequest(Request.Method.POST, "http://project-db-stu.ddns.net:1524/AIM_DBServer/BoSelectServlet2",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답을 처리
                        String[] str = response.split(",");
                        txt_uname.setText(str[0]);
                        txt_bs.setText(str[1]);
                        txt_b.setText(str[2]);
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
                return params;
            }
        };

        requestQueue.add(StringRequest_selectBo);

        txt_url1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Introduce = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.samsunglife.com/"));
                startActivity(Introduce);
            }
        });

        txt_url2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Introduce = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.samsunglife.com/individual/mysamsunglife/insurance/internet/MDP-MYINT020110M?int=Link%3ADPC%3Afooter/"));
                startActivity(Introduce);
            }
        });

        txt_url3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Introduce = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.samsunglife.com/individual/cs/faq/MDP-CUOAQ010000M/"));
                startActivity(Introduce);
            }
        });

        img_backinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo).commit();

            }
        });


        return view;
    }
}