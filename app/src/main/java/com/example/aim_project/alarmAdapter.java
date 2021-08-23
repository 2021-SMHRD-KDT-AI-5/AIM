package com.example.aim_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class alarmAdapter extends BaseAdapter {

//    DBManager manager;
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<AlarmVO> data;



//    public alarmAdapter(DBManager manager) {
//        this.manager = manager;
//    }

    public alarmAdapter(Context context, int layout, ArrayList<AlarmVO> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(layout,parent,false);
        }
        TextView tv_alarm_id = convertView.findViewById(R.id.tv_alarm_id);
        TextView tv_alarm_name = convertView.findViewById(R.id.tv_alarm_name);
        TextView tv_alarm_date = convertView.findViewById(R.id.tv_alarm_date);
        TextView tv_alarm_contents = convertView.findViewById(R.id.tv_alarm_contents);

        tv_alarm_id.setText(data.get(position).getId());
        tv_alarm_name.setText(data.get(position).getBabyName());
        tv_alarm_date.setText(data.get(position).getDate());
        tv_alarm_contents.setText(data.get(position).getCry_move());





        return convertView;
    }
}
