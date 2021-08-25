package com.example.aim_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class FragmentMyinfo_6_ extends Fragment {

    ImageView img_backinfo;
    FragmentMyinfo fragmentMyinfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_myinfo_6_, container, false);
        img_backinfo = view.findViewById(R.id.img_backinfo);
        fragmentMyinfo = new FragmentMyinfo();

        WebView wb = view.findViewById(R.id.wb_youtube);

                            // 나중에 url 바꾸기
        String address = "https://www.youtube.com/watch?v=-fvEJgCNURQ&list=PLoGFWG18d4dI85IJlJDBIm_SvBniZ5QSh/";

        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wb.loadUrl(address);
        wb.setWebViewClient(new WebViewClient());


        img_backinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragmentMyinfo).commit();
            }
        });

        return view;
    }
}