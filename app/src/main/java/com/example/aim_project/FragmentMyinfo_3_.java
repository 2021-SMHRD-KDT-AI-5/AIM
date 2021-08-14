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

public class FragmentMyinfo_3_ extends Fragment {

    Button button, button2, button3, button4, button5;
    NotificationManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo_3_, container, false);

        button = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                if (Build.VERSION.SDK_INT >= 26) {                         // 지속시간        음량
                    vibrator.vibrate(VibrationEffect.createOneShot(1000, 30));
                } else {
                    vibrator.vibrate(1000);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(getActivity().getApplicationContext(), uri);
                ringtone.play();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(getActivity().getApplicationContext(),R.raw.bgsound);
                player.start();
            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoti1();
            }
        });


        return view;

}

private  static String CHANNEL_ID = "channel1";
private  static String CHANNEL_NAME = "channel1";

public void showNoti1(){

    manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
    NotificationCompat.Builder builder = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        if(manager.getNotificationChannel(CHANNEL_ID) == null){
            manager.createNotificationChannel(new NotificationChannel(
                    CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT
            ));
            builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID);
        }
    }else{
        builder = new NotificationCompat.Builder(getActivity());
    }

    builder.setContentTitle("간단 알림2");
    builder.setContentText("알림 메세지입니다.");
    builder.setSmallIcon(android.R.drawable.ic_menu_view);
    Notification noti = builder.build();

    manager.notify(1, noti);

    }

}
