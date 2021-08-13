package com.example.aim_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.Map;

public class FragmentMyinfo_2_addBo extends Fragment {

    FragmentMyinfo_2_noBo fragmentMyinfo_2_noBo_;
    FragmentMyinfo_2_ifBo fragmentMyinfo_2_ifBo;
    Spinner spinner, spinner2;
    TextView txt_jo;
    EditText txt_1,txt_2,txt_3,txt_4;
    Button btn_ok,btn_no,btn_lookup;
    RequestQueue requestQueue;
    StringRequest StringRequest_bohuminsert; // 정보 조회

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo_2_add_bo, container, false);
        fragmentMyinfo_2_noBo_ = new FragmentMyinfo_2_noBo();
        fragmentMyinfo_2_ifBo = new FragmentMyinfo_2_ifBo();
        btn_no = view.findViewById(R.id.btn_no);
        btn_ok = view.findViewById(R.id.btn_ok);
        btn_lookup = view.findViewById(R.id.btn_lookup);
        spinner = view.findViewById(R.id.spinner);
        spinner2 = view.findViewById(R.id.spinner2);
        txt_1 = view.findViewById(R.id.txt_1);
        txt_2 = view.findViewById(R.id.txt_2);
        txt_3 = view.findViewById(R.id.txt_3);
        txt_4 = view.findViewById(R.id.txt_4);
        txt_jo = view.findViewById(R.id.txt_jo);

        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");   // 보험 db 등록에 필요할 id

        // 스피너 1 (보험사)
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.test, android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(monthAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_1.setText(parent.getItemAtPosition(position)+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // 스피너 2 (보험이름)  =>  나중에 보험사에 따라 보험이름 바뀌게 다중스피너 조건문으로 ㄱㄱ
        ArrayAdapter monthAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.test2, android.R.layout.simple_spinner_dropdown_item);
        monthAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(monthAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_2.setText(parent.getItemAtPosition(position)+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // requestQueue 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest_bohuminsert = new StringRequest(Request.Method.POST,"http://172.30.1.1:8086/AIM_DBServer/BohumServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //응답처리
                        Toast.makeText(getActivity().getApplicationContext(),"보험 정보가 등록되었습니다.",Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMyinfo_2_ifBo).commit();
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

                String u_name = txt_3.getText().toString();
                String bs_name = txt_1.getText().toString();
                String b_name = txt_2.getText().toString();

                params.put("id", u_id);
                params.put("u_name", u_name);
                params.put("bs_name", bs_name);
                params.put("b_name", b_name);

                return params;
            }
        };


        btn_lookup.setOnClickListener(new View.OnClickListener() {  // 조회버튼
            @Override
            public void onClick(View v) {
                txt_jo.setText("조회가 성공적으로 되었습니다.");
                // 보험을 가입했는지의 여부는, 여기서 값을 받아서 해당 보험사에 조회해서 가입여부를 알려주는 흐름으로 하는게 맞는데
                // 그게 너무 복잡하니까 일단 조건없이 조회가 되는걸로 하는걸로
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {  // 등록 버튼
            @Override
            public void onClick(View v) {
                String str = txt_jo.getText().toString();
                if(str.equals("조회가 성공적으로 되었습니다.")){ // 조회 했으면 등록해버리기
                    requestQueue.add(StringRequest_bohuminsert);

                }else{  // 조회 안했으면 토스메세지
                    Toast.makeText(getActivity().getApplicationContext(),"조회를 먼저 진행해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {  // 취소 버튼
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMyinfo_2_noBo_).commit();

            }
        });

        return view;
    }


}