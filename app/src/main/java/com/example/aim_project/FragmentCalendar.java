package com.example.aim_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class FragmentCalendar extends Fragment {

      EditText edt_title,edt_write;
      Button btn_ok;
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public FragmentCalendar() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FragmentCalendar.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FragmentCalendar newInstance(String param1, String param2) {
//        FragmentCalendar fragment = new FragmentCalendar();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        edt_title = view.findViewById(R.id.edt_title);
        edt_write = view.findViewById(R.id.edt_write);
        btn_ok =  view.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title",edt_title.getText().toString());
                bundle.putString("write",edt_write.getText().toString());
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragmentDiary_1 fragmentDiary_1 = new FragmentDiary_1();
                fragmentDiary_1.setArguments(bundle);
                transaction.replace(R.id.container2,fragmentDiary_1);
                transaction.commit();


            }
        });


        return view;
    }
}