package com.example.aim_project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import static android.content.Context.NOTIFICATION_SERVICE;

public class FragmentMyinfo_3_noLicense extends Fragment {

    Button btn_buyLicense,btn_enterCode;

    FragmentMyinfo_3_enterCode fragmentMyinfo_3_enterCode;
    FragmentMyinfo_3_pay fragmentMyinfo_3_pay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo_3_, container, false);

        btn_buyLicense = view.findViewById(R.id.btn_buyLicense);
        btn_enterCode = view.findViewById(R.id.btn_enterCode);
        fragmentMyinfo_3_enterCode = new FragmentMyinfo_3_enterCode();
        fragmentMyinfo_3_pay = new FragmentMyinfo_3_pay();



        btn_enterCode.setOnClickListener(new View.OnClickListener() {       // 코드입력으로 go
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_3_enterCode).commit();
            }
        });

        btn_buyLicense.setOnClickListener(new View.OnClickListener() {      // 결제창으로 go
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentMyinfo_3_pay).commit();
            }
        });

        return view;

    }

}
