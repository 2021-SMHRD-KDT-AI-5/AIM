package com.example.aim_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class FragmentMyinfo_6_ extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_myinfo_6_, container, false);

        WebView wb = view.findViewById(R.id.wb_youtube);

                            // 나중에 url 바꾸기
        String address = "https://www.youtube.com/watch?v=-fvEJgCNURQ&list=PLoGFWG18d4dI85IJlJDBIm_SvBniZ5QSh/";

        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wb.loadUrl(address);
        wb.setWebViewClient(new WebViewClient());

        return view;
    }
}