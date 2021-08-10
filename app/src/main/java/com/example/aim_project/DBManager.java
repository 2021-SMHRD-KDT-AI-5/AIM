package com.example.aim_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    DBHelper helper;

    public DBManager(Context context){ helper = new DBHelper(context); }

    // 회원가입 메소드
    public void join(String id, String pw, String email, String ip){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into aim_members values('"+ id +"','"+ pw +"','"+ email +"','"+ ip +"')");
    }

    // 아기 정보 신규 저장 메소드
    public void baby_join(String id, String babyName, String babyBirthday){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into baby_info values('"+ id +"','"+ babyName +"','"+ babyBirthday +"')");
    }

    // 로그인 메소드
    public boolean login(String id, String pw){
        SQLiteDatabase db = helper.getReadableDatabase();

        // SQL문 전달
        Cursor cursor = db.rawQuery("select member_id from aim_members where member_id = '"+id+"' and password = '"+pw+"'", null);

        // 로그인 성공/실패 여부(성공시 true, 실패시 false)
        boolean login_success = false;

       if(cursor.moveToNext()){ // DB에 id, pw가 존재할 경우
           login_success = true;
       }
       return login_success;
    }
}
