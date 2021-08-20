package com.example.aim_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;


public class FragmentMain extends Fragment {

    DBManager manager; // 로그인을 위한 DBManager 객체 생성

    TextView tv_dday, tv_tip;
    int GET_GALLARY_IMAGE1 = 100;
    int GET_GALLARY_IMAGE2 = 200;
    ImageView img_parent_profile, img_baby_profile;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMain() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        tv_dday = view.findViewById(R.id.tv_dday);
        tv_tip = view.findViewById(R.id.tv_tip);
        img_parent_profile = view.findViewById(R.id.img_parent_profile);
        img_baby_profile = view.findViewById(R.id.img_baby_profile);

        manager = new DBManager(getActivity().getApplicationContext()); // 로그인을 위한 DBManager 객체 생성

        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");

        manager.loginOpUpdate(u_id);

        // 팁 출력
        tv_tip.setText(manager.getTip());

        // 로그인중인 회원 아이디 기반으로 아기 생일 가져오기
        String birthday = null;

        try {
            birthday = new JSPTask("getBirthday").execute("getBirthday",u_id).get();
            birthday = Html.fromHtml(birthday).toString().trim(); // html태그 제거 및 공백 제거
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tv_dday.setText("D + " + Dday_cal(birthday));
//        tv_dday.setText(birthday);

        // 프로필 사진 클릭시 부모와 아이 프로필사진 바꾸기
        img_parent_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, GET_GALLARY_IMAGE1);
            }
        });

        img_baby_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, GET_GALLARY_IMAGE2);
            }
        });

        return view;

    }

    // D-day 계산 메소드
    public long Dday_cal(String birthday){
        int birthdayInt = Integer.parseInt(birthday);
        int birthdayYear = birthdayInt/10000;
        int birthdayMonth = (birthdayInt%10000)/100;
        int birthdayDate = birthdayInt%100;

        Calendar today = Calendar.getInstance(); //현재 오늘 날짜
        Calendar dday = Calendar.getInstance();

        dday.set(birthdayYear, birthdayMonth+1, birthdayDate);

        long day = dday.getTimeInMillis()/86400000;
        long tday = today.getTimeInMillis()/86400000;

        long count = tday - day + 1; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.

        return count;
    }

    // 프로필사진 선택 후 응답받는 곳
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GET_GALLARY_IMAGE1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri selectedImageUri = data.getData();
            img_parent_profile.setImageURI(selectedImageUri);
        }else if(requestCode==GET_GALLARY_IMAGE2 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri selectedImageUri = data.getData();
            img_baby_profile.setImageURI(selectedImageUri);
        }
    }

}