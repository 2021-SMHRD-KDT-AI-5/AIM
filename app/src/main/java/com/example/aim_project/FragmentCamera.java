package com.example.aim_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.Switch;


public class FragmentCamera extends Fragment {

    Switch swc1;
    WebView wb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        swc1 = view.findViewById(R.id.swc1);
        wb = view.findViewById(R.id.webview);

        swc1.setChecked(true);
        String address = "https://corona-live.com/";

        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wb.loadUrl(address);
        wb.setWebViewClient(new WebViewClient());

        // 스위치 리스너
        swc1.setOnCheckedChangeListener(new visibilitySwitchListener());

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
}