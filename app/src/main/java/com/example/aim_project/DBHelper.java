package com.example.aim_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public  DBHelper(Context context){
        super(context, "AIMDataBase2.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE loginset(recent_user_id VARCHAR2(30) NOT NULL, remember_id VARCHAR2(10), autologin VARCHAR2(10))");

        db.execSQL("CREATE TABLE arlam_id(member_id VARCHAR2(30))");


        db.execSQL("CREATE TABLE aim_members(\n" +
                "member_id VARCHAR2(30),\n" +
                "password VARCHAR2(30) NOT NULL,\n" +
                "email VARCHAR2(30) NOT NULL,\n" +
                "ip VARCHAR2(30) NOT NULL,\n" +
                "CONSTRAINT members_pk PRIMARY KEY(member_id))");

        db.execSQL("CREATE TABLE baby_info(\n" +
                "parent_id VARCHAR2(30),\n" +
                "baby_name VARCHAR2(30) NOT NULL,\n" +
                "birthday VARCHAR2(30) NOT NULL,\n" +
                "CONSTRAINT baby_info_fk FOREIGN KEY(parent_id)\n" +
                "REFERENCES aim_members(member_id)\n" +
                ")");

        db.execSQL("CREATE TABLE baby_diary(\n" +
                "member_id VARCHAR2(30) NOT NULL,\n" +
                "baby_name VARCHAR2(30) NOT NULL,\n" +
                "diary_date VARCHAR2(30) NOT NULL,\n" +
                "title VARCHAR2(200) NOT NULL,\n" +
                "contents VARCHAR2(3000),\n" +
                "CONSTRAINT baby_diary_fk FOREIGN KEY(member_id)\n" +
                "REFERENCES aim_members(member_id)\n" +
                ")");

        db.execSQL("CREATE TABLE alarm_log(\n" +
                "member_id VARCHAR2(30) NOT NULL,\n" +
                "baby_name VARCHAR2(30) NOT NULL,\n" +
                "alarm_date VARCHAR2(30) NOT NULL,\n" +
                "cry_move VARCHAR2(30) NOT NULL,\n" +
//                "contents VARCHAR2(200) NOT NULL,\n" +
                "CONSTRAINT alarm_log_fk FOREIGN KEY(member_id)\n" +
                "REFERENCES aim_members(member_id)\n" +
                ")");


        db.execSQL("CREATE TABLE tips(\n" +
                "tip VARCHAR2(3000) NOT NULL\n" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table aim_members");
        onCreate(db);
    }
}
