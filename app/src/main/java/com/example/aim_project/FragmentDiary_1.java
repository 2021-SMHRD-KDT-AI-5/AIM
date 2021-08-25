package com.example.aim_project;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;


public class FragmentDiary_1 extends Fragment {
    FragmentDiary_1 fragmentDiary_1;
    FragmentMain fragmentMain;
    TextView tv_title;
    com.prolificinteractive.materialcalendarview.MaterialCalendarView calendarView;
    Button btn_write;
    EditText edt_title,edt_write;
    private String title;
    private String write;
    ArrayList<DiaryVO> dateList = new ArrayList<>();
    ArrayList<DiaryVO> datepushList = new ArrayList<>();
    CalendarDay[] draw = new CalendarDay[1];
    String draw_str = "";
    DBManager manager;
    Dialog dilaog01,dilaog02,dilaog03,dilaog04,dilaog05;
    ImageView img_back_info2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diary_1, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        fragmentDiary_1 = new FragmentDiary_1();
        fragmentMain = new FragmentMain();
        tv_title = view.findViewById(R.id.tv_title);
        btn_write = view.findViewById(R.id.btn_write);
        img_back_info2 = view.findViewById(R.id.img_back_info2);

        dilaog01 = new Dialog(getActivity());
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog01.setContentView(R.layout.dialog_diary_write);

        dilaog02 = new Dialog(getActivity());
        dilaog02.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog02.setContentView(R.layout.dialog_diary_read);

        dilaog03 = new Dialog(getActivity());
        dilaog03.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog03.setContentView(R.layout.dialog08_ins_no_yes2);

        dilaog04 = new Dialog(getActivity());
        dilaog04.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog04.setContentView(R.layout.dialog09_ins_delete);

        dilaog05 = new Dialog(getActivity());
        dilaog05.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog05.setContentView(R.layout.dialog09_ins_delete);

        manager = new DBManager(getActivity().getApplicationContext());

        Intent it_login = getActivity().getIntent();
        String u_id = it_login.getStringExtra("loginId");

//-------------------------------------------- 초기 셋팅 ------------------------------------------------------//

        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.addDecorators(
                new Decorator_Sunday(),
                new Decorator_Saturday(),
                new Decorator_onDat()
        );

        dateList = manager.diary_select_id(u_id);

        //String [] list1 = {"2021-08-18","2021-08-19","2021-08-20"};

        for(int i = 0; i < dateList.size(); i++){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            CalendarDay date12 = null;

            try {
                date12 = CalendarDay.from(formatter.parse(dateList.get(i).getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendarView.addDecorators(
                    new Decorator_Event(Color.RED, Collections.singleton(date12))
            );
        }

//------------------------------------------ 다이얼로그 종료 되었을경우 ----------------------------------------------------//

        // 작성했을때
        // 점찍기 , 타이틀 띄워주기 , 버튼 텍스트 수정
        dilaog05.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                btn_write.setText("작성글 확인");

                String title = manager.diary_select_title(u_id,draw_str);

                tv_title.setText("제목 : "+title);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                CalendarDay date_1 = null;

                try {
                    date_1 = CalendarDay.from(formatter.parse(draw_str));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calendarView.addDecorators(
                        new Decorator_Event(Color.RED, Collections.singleton(date_1))
                );

            }

        });

        // 삭제 했을때
        // 점빼기 , 타이틀 삭제 , 버튼 텍스트 수정
        dilaog04.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                btn_write.setText("글 작성");
                tv_title.setText("");

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                CalendarDay date_2 = null;

                try {
                    date_2 = CalendarDay.from(formatter.parse(draw_str));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calendarView.addDecorators(
                        new Decorator_Event(Color.WHITE, Collections.singleton(date_2))
                );


            }

        });
//-------------------------------------------- 클릭이벤트 (캘린더 클릭할때) ------------------------------------------------------//

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {

                String str1 = date.toString();
                String [] str2 = str1.split("-");
                String aa = str1.replace("CalendarDay{","");
                String aaa = aa.replace("}","");
                String [] last1 = aaa.split("-");

                int year = Integer.parseInt(last1[0]);
                int month = Integer.parseInt(last1[1])+1;
                int dayOfMonth = Integer.parseInt(last1[2]);
                String newDay = year+"-"+month+"-"+dayOfMonth;

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                CalendarDay date1 = null;

                try {
                    date1 = CalendarDay.from(formatter.parse(newDay));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                draw[0] = date1;
                draw_str = newDay;

                String str_title = manager.diary_select_title(u_id,newDay);

                if(str_title.equals("")){
                    tv_title.setText("");
                    btn_write.setText("글 작성");
                }else{
                    tv_title.setText("제목 : "+str_title);
                    btn_write.setText("작성글 확인");
                }

            }
        });


//-------------------------------------------- 클릭이벤트 (버튼) ------------------------------------------------------//

        // 글 작성 or 작성글 확인
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*              Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putString("title",draw[0]+"");//번들에 넘길 값 저장
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentCalendar.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                transaction.replace(R.id.container2, fragmentCalendar);
                transaction.commit();*/


                // 작성
                if(btn_write.getText().toString().equals("글 작성")) {
                    showDialog01(u_id, draw_str);

                    // 수정
                }else{
                    datepushList = manager.diary_select_id_date(u_id,draw_str);

                    String id = datepushList.get(0).getId();
                    String date = datepushList.get(0).getDate();
                    String title = datepushList.get(0).getTitle();
                    String contents = datepushList.get(0).getContents();

                    showDialog02(id,date,title,contents);
                }

            }
        });

        img_back_info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragmentMain).commit();
            }
        });


        return view;
    }

//-------------------------------------------- 다이얼로그 함수 ------------------------------------------------------//

    // dialog01을 디자인하는 함수
    public void showDialog01(String id,String draw_str) {
        dilaog01.show();
        EditText edt_title2 = dilaog01.findViewById(R.id.edt_title2);
        EditText edt_write2 = dilaog01.findViewById(R.id.edt_write2);

        edt_title2.setText("");
        edt_write2.setText("");


        // 취소
        dilaog01.findViewById(R.id.btn_cancel2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog01.dismiss();          // 다이얼로그 닫기
            }
        });

        // 작성
        dilaog01.findViewById(R.id.btn_ok2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edt_title2.getText().toString();
                String contents = edt_write2.getText().toString();

                //Toast.makeText(getContext(),""+id+" "+draw_str+" "+title+" "+contents,Toast.LENGTH_SHORT).show();

                manager.diarywrite(id,draw_str,title,contents);
                showDialog05();
                dilaog01.dismiss();
            }
        });
    }



    // dialog02을 디자인하는 함수
    public void showDialog02(String id,String date, String title, String contents) {
        dilaog02.show();

        EditText edt_title2, edt_write2;
        edt_title2 = dilaog02.findViewById(R.id.edt_title1);
        edt_write2 = dilaog02.findViewById(R.id.edt_write1);

        edt_title2.setText(title);
        edt_write2.setText(contents);

        // 취소
        dilaog02.findViewById(R.id.btn_cancel2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog02.dismiss();          // 다이얼로그 닫기
            }
        });

        // 수정
        dilaog02.findViewById(R.id.btn_ok2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String page_title = edt_title2.getText().toString();
                String page_contents = edt_write2.getText().toString();

                manager.diaryUpdate(id,date,page_title,page_contents);
                //Toast.makeText(getContext(),""+id+" "+date+" "+page_title+" "+page_contents,Toast.LENGTH_SHORT).show();

                dilaog02.dismiss();
            }
        });

        // 삭제
        dilaog02.findViewById(R.id.btn_delete2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog03(id,date,title,contents);
                dilaog02.dismiss();

            }
        });
    }


    // dialog03을 디자인하는 함수
    public void showDialog03(String id,String date, String title, String contents) {
        dilaog03.show();

        TextView txt_really;
        txt_really = dilaog03.findViewById(R.id.txt_really);
        txt_really.setText("작성글을 삭제하시겠습니까?");

        // 취소
        dilaog03.findViewById(R.id.btn_nope).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog02(id,date,title,contents);
                dilaog03.dismiss();          // 다이얼로그 닫기
            }
        });

        // 삭제
        dilaog03.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                manager.diaryDelete(id,date);
                showDialog04();
                dilaog03.dismiss();
            }
        });
    }


    // dialog04을 디자인하는 함수
    public void showDialog04() {
        dilaog04.show();

        dilaog04.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog04.dismiss();
            }
        });
    }


    // dialog05을 디자인하는 함수
    public void showDialog05() {
        dilaog05.show();

        TextView txt_main12;
        txt_main12 = dilaog05.findViewById(R.id.txt_main12);
        txt_main12.setText("다이어리가 작성되었습니다.");

        dilaog05.findViewById(R.id.btn_yes_r2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog05.dismiss();
            }
        });
    }


}