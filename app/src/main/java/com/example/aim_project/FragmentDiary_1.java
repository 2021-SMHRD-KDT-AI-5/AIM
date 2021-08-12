package com.example.aim_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class FragmentDiary_1 extends Fragment {
    FragmentDiary_1 fragmentDiary_1;
    FragmentCalendar fragmentCalendar;
    TextView tv_1,tv_2;
    CalendarView calendar1_view;
    EditText edt_title,edt_write;
    private String title;
    private String write;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diary_1, container, false);

        calendar1_view = view.findViewById(R.id.calendar1_view);
        fragmentDiary_1 = new FragmentDiary_1();
        fragmentCalendar = new FragmentCalendar();



        calendar1_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentCalendar).commit();



            }
        });


        fragmentCalendar = new FragmentCalendar();
        CalendarView calendarView = view.findViewById(R.id.calendar1_view);

        tv_1 = view.findViewById(R.id.tv_1);
        tv_2 = view.findViewById(R.id.tv_2);
        edt_title = view.findViewById(R.id.edt_title);
        edt_write = view.findViewById(R.id.edt_write);

        if (getArguments() != null) {
            title = getArguments().getString("title");
            write = getArguments().getString("write");
            tv_1.setText(title);
            tv_2.setText(write);

        }


        return view;
    }
}