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
import android.widget.EditText;
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

public class FragmentMyinfo_3_enterCode extends Fragment {

    EditText edt_codePo1, edt_codePo2, edt_codePo3;
    Button btn_codeCheck, btn_codeCancel, btn_codeUse;
    TextView txt_codeCheck;
    FragmentMyinfo_3_finish fragmentMyinfo_3_finish;
    RequestQueue requestQueue;
    StringRequest StringRequest_insertLicense;
    Dialog dilaog01,dilaog02;

    // 정액권 코드 정의
    String[] codepo1 = {"000000"};                         // 코드입력 첫번째칸
    String[] codepo2 = {"000000"};                         // 코드입력 두번째칸
    String[] codepo3 = {"000001", "000006", "000012"};       // 코드입력 세번째칸
    // 000001 =  서비스오픈 이벤트 코드(1개월)
    // 000006 =  보험사 연계 코드(6개월)
    // 000012 =  보험사 연계 코드(12개월)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo_3_enter_code, container, false);

        edt_codePo1 = view.findViewById(R.id.edt_codePo1);
        edt_codePo2 = view.findViewById(R.id.edt_codePo2);
        edt_codePo3 = view.findViewById(R.id.edt_codePo3);
        txt_codeCheck = view.findViewById(R.id.txt_codeCheck);
        btn_codeCheck = view.findViewById(R.id.btn_codeCheck);
        btn_codeCancel = view.findViewById(R.id.btn_codeCancel);
        btn_codeUse = view.findViewById(R.id.btn_codeUse);
        fragmentMyinfo_3_finish = new FragmentMyinfo_3_finish();

        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");

        dilaog01 = new Dialog(getActivity());
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog01.setContentView(R.layout.dialog16_usecode);

        dilaog02 = new Dialog(getActivity());
        dilaog02.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog02.setContentView(R.layout.dialog17_checkcode);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest_insertLicense = new StringRequest(Request.Method.POST, "http://project-db-stu.ddns.net:1524/AIM_DBServer/LicenseServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답을 처리
                        if (response.equals("1")) {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragmentMyinfo_3_finish).commit();
                        } else {
                            Toast.makeText(getContext(),"등록실패", Toast.LENGTH_SHORT).show();
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

                String str = edt_codePo3.getText().toString().substring(4,6);

                params.put("id", u_id);  //license_type   codeOrNot
                params.put("license_type", str);
                params.put("codeOrNot", "yeah");
                params.put("where","insert");

                return params;
            }
        };


        /////----------------------- 클릭이벤트 -----------------------/////

        // 사용된 코드를 저장할 db를 오라클에서 만들어서 관리할까 했는데 (재사용 못하게)
        // 어플 출시할때 결제대신, 코드 입력해서 사용해보라고 하는게 좋을거 같아서 => 사람들한테 다 다른코드 나눠주기 힘들것같아서
        // 이부분은 추후에 수정한다는 방향으로 가는게 좋을것 같습니다.


        btn_codeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codePo1 = edt_codePo1.getText().toString();
                String codePo2 = edt_codePo2.getText().toString();
                String codePo3 = edt_codePo3.getText().toString();

                if (codePo1.equals(codepo1[0]) && codePo2.equals(codepo2[0])) {   // 존재하는 코드를 제대로 입력한경우
                    if (codePo3.equals(codepo3[0])) {
                        txt_codeCheck.setText("서비스오픈 이벤트 코드(1개월)");
                    } else if (codePo3.equals(codepo3[1])) {
                        txt_codeCheck.setText("보험사 연계 코드(6개월)");
                    } else if (codePo3.equals(codepo3[2])) {
                        txt_codeCheck.setText("보험사 연계 코드(12개월)");
                    }else{
                        txt_codeCheck.setText("존재하지 않거나 이미 사용된 코드입니다.");
                    }
                } else {  // 입력을 잘못한경우
                    txt_codeCheck.setText("존재하지 않거나 이미 사용된 코드입니다.");
                }
            }
        });


        btn_codeUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = txt_codeCheck.getText().toString();
                String codePo1 = edt_codePo1.getText().toString();
                String codePo2 = edt_codePo2.getText().toString();
                String codePo3 = edt_codePo3.getText().toString();

                if (codePo1.equals(codepo1[0]) && codePo2.equals(codepo2[0])) {   // 존재하는 코드를 제대로 입력한경우
                    if (codePo3.equals(codepo3[0])) {
                        showDialog01();
                    } else if (codePo3.equals(codepo3[1])) {
                        showDialog01();
                    } else if (codePo3.equals(codepo3[2])) {
                        showDialog01();
                    }else{
                        showDialog02();
                    }
                } else {  // 입력을 잘못한경우
                    showDialog02();
                }
            }
        });

        return view;
    }

    public void showDialog01(){
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
                requestQueue.add(StringRequest_insertLicense);
                dilaog01.dismiss();

            }
        });
    }

    public void showDialog02(){
        dilaog02.show();

        dilaog02.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dilaog02.dismiss();
            }
        });
    }

}