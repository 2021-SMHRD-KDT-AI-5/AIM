package com.example.aim_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class FragmentCamera extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        WebView wb = view.findViewById(R.id.webview);

        String address = "http://172.30.1.14:5000/";

        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wb.loadUrl(address);
        wb.setWebViewClient(new WebViewClient());


        return view;
    }
}