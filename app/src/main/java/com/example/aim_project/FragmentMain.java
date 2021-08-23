package com.example.aim_project;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;


public class FragmentMain extends Fragment {

    DBManager manager; // 로그인을 위한 DBManager 객체 생성

    TextView tv_dday, tv_tip;
    int GET_GALLARY_IMAGE1 = 100;
    int GET_GALLARY_IMAGE2 = 200;
    int CODE_ALBUM_REQUEST = 111;
    ImageView img_parent_profile, img_baby_profile;
    String parentPic, babyPic;
    String u_id;
    Button btn_addPic;
    RecyclerView rv_pictures;

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
        btn_addPic = view.findViewById(R.id.btn_addPic);
        rv_pictures = view.findViewById(R.id.rv_pictures);

        // 리싸이클러뷰를 가로로 설정
        rv_pictures.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));

        manager = new DBManager(getActivity().getApplicationContext()); // 로그인을 위한 DBManager 객체 생성

        Intent it_login = getActivity().getIntent();
        u_id = it_login.getStringExtra("loginId");

        manager.loginOpUpdate(u_id);

        manager.loginOpUpdate(u_id);

//        boolean test = manager.arlam_id_Check();
//
//        if (test == true){  // 이미 있을경우
//            manager.arlamUpdate(u_id);
//        }else{  // 없을경우 (앱 처음시작시)
//            manager.arlam_id(u_id);
//        }

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

        // 프로필사진 관련
        //=========================================================================================================
        // 부모와 아기 프로필사진 불러오기
        try {
            parentPic = new JSPTask("getParentProfilePic").execute("getParentProfilePic",u_id).get();
            parentPic = Html.fromHtml(parentPic).toString().trim();
            babyPic = new JSPTask("getBabyProfilePic").execute("getBabyProfilePic",u_id).get();
            babyPic = Html.fromHtml(babyPic).toString().trim();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!parentPic.equals("defult")){
            try{
                Toast.makeText(getContext(),parentPic,Toast.LENGTH_SHORT).show();
                img_parent_profile.setImageURI(Uri.parse(parentPic));
            } catch (Exception e){
                Toast.makeText(getContext(),"프로필사진이 경로에 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
            }
        }else{
            img_parent_profile.setImageResource(R.drawable.parents);
        }

        if(!babyPic.equals("defult")){
            try{
                img_baby_profile.setImageURI(Uri.parse(babyPic));
            } catch (Exception e){
                Toast.makeText(getContext(),"프로필사진이 경로에 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
            }
        }else {
            img_baby_profile.setImageResource(R.drawable.baby);
        }

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
        //=========================================================================================================

        // 사진 리스트(리싸이클러 뷰) 관련
        //=========================================================================================================
        // 사진추가 버튼 클릭시 갤러리 열기
        btn_addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,CODE_ALBUM_REQUEST);
            }
        });
        //=========================================================================================================
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

            // 선택한 사진의 경로를 오라클DB에 저장
            try {
                String setParentProfile = new JSPTask("setParentProfilePic").execute("setParentProfilePic",u_id,selectedImageUri.toString()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if(requestCode==GET_GALLARY_IMAGE2 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri selectedImageUri = data.getData();
            img_baby_profile.setImageURI(selectedImageUri);

            // 선택한 사진의 경로를 오라클DB에 저장
            try {
                String setBabyProfile = new JSPTask("setBabyProfilePic").execute("setBabyProfilePic",u_id,selectedImageUri.toString()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 리사이클러 뷰에 들어갈 이미지 가져오기
        }else if(requestCode==CODE_ALBUM_REQUEST && resultCode==RESULT_OK && data != null){
            ArrayList<Uri> uriList = new ArrayList<>();
            if(data.getClipData() != null){
                ClipData clipData = data.getClipData();
                if(clipData.getItemCount() > 10){ // 선택한 이미지가 10개를 초과했을 경우
                    Toast.makeText(getActivity().getApplicationContext(),"사진은 10개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }else if(clipData.getItemCount()==1){ // 다중 선택에서 하나만 선택한 경우
                    Uri filePath = clipData.getItemAt(0).getUri();
                    uriList.add(filePath);
                } else if (clipData.getItemCount() > 1 && clipData.getItemCount() <= 10) { // 2개 이상 10개 이하로 선택했을 경우
                    for(int i = 0; i < clipData.getItemCount(); i++){
                        uriList.add(clipData.getItemAt(i).getUri());
                    }
                }
            }
            // 리사이클러 뷰에 보여주기
            PictureAdapter adapter = new PictureAdapter(uriList,getActivity().getApplicationContext());
            rv_pictures.setAdapter(adapter);
        }
    }

}