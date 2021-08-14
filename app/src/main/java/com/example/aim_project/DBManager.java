package com.example.aim_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    DBHelper helper;

    String str = "nope";

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



    // 로그인 옵션 체크 메소드

    public boolean loginOpCheck(){
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select recent_user_id from loginset",null);

        // 로그인 옵션 있는지/없는지 여부(있으면 true, 없으면 false)
        boolean login_success = false;

        if(cursor.moveToNext()){ // 내장 DB에 이미 로그인옵션 테이블이 있을경우
            login_success = true;
        }
        return login_success;
    }



    // 로그인 옵션 기본셋팅(default = no)        사용자가 맨 처음 어플 사용할때 자동생성 str == no
    public void loginOpDefault(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into loginset values('"+ str +"','"+ str +"','"+ str +"')");
    }


    // 로그인 옵션 반환
    public String[] loginOp_return(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from loginset",null);

        String[] opList = new String[3];

        if(cursor.moveToNext()){ // 내장 DB에 이미 로그인옵션 테이블이 있을경우

            opList[0] = cursor.getString(0);    // 최근 로그인 아이디
            opList[1] = cursor.getString(1);    // 아이디 기억
            opList[2] = cursor.getString(2);    // 자동로그인
        }

        return opList;
    }

    public void loginOpUpdate(String id){   // 아이디를 최근 로그인한 아이디로 변경
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update loginset set recent_user_id ='"+id+"'");
        db.close();
    }


}
