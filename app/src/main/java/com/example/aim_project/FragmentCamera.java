package com.example.aim_project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;


public class FragmentCamera extends Fragment {

    Switch swc1;
    WebView wb;
    Dialog dilaog01;
    ImageView img_back_info0;
    FragmentMain fragmentMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        wb = view.findViewById(R.id.webview);
        swc1 = view.findViewById(R.id.swc1);
        img_back_info0 = view.findViewById(R.id.img_back_info0);

        dilaog01 = new Dialog(getActivity());
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog01.setContentView(R.layout.dialog_cameraloading);

        fragmentMain = new FragmentMain();

        String address = "http://172.30.1.14:5000/";

        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wb.loadUrl(address);
        wb.setWebViewClient(new WebViewClient());

        swc1.setChecked(false);
        wb.setVisibility(View.INVISIBLE);

        // 스위치 리스너
        swc1.setOnCheckedChangeListener(new visibilitySwitchListener());

        //////--------------------- 애니메이션 구간 ---------------------------/////

        Animation anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.rotate);

        Animation.AnimationListener aniListener01 = new Animation.AnimationListener() { // 로고
            public void onAnimationEnd(Animation animation) { // 애니메이션이 끝났을 때
                dilaog01.dismiss();
            }
            @Override
            public void onAnimationRepeat(Animation animation) { }      // 반복할때
            @Override
            public void onAnimationStart(Animation animation) { }
        };

        anim.setAnimationListener(aniListener01);       // 리스너에 애니메이션 등록

        showDialog01(anim); // 다이얼로그 띄워서 그안에서 애니메이션 실행

        /////----------------------------------------------------------------/////


        img_back_info0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragmentMain).commit();
            }
        });



        return view;
    }

    // 스위치가 on/off일 때 반응하는 클래스
    class visibilitySwitchListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                wb.setVisibility(View.VISIBLE);
                swc1.setText("영상 재생중");
            }else {
                wb.setVisibility(View.INVISIBLE);
                swc1.setText("영상 재생 중지");
            }
        }
    }


    // dialog01을 디자인하는 함수
    public void showDialog01(Animation anim) {
        dilaog01.show();

        ImageView img_loading;
        img_loading = dilaog01.findViewById(R.id.img_loading);

        img_loading.startAnimation(anim); // 애니메이션 실행

    }

}