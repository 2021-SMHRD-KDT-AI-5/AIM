package com.example.aim_project;

import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSPTask extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;

    JSPTask(String sendmsg){
        this.sendMsg = sendmsg;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;

            // 접속할 서버 주소 (이클립스에서 .jsp 실행시 웹브라우저 주소)
            URL url = new URL("http://172.30.1.15:8090/AIM_DBServer/ConnectDB.jsp");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            // 전송할 데이터. GET 방식으로 작성
            if (sendMsg.equals("getBirthday")) { // 아기 생일 가져오기
                sendMsg = "task=" + strings[0] + "&id=" + strings[1];
            }else if(sendMsg.equals("joinMember")){ // 회원가입
                sendMsg = "task=" + strings[0] + "&id=" + strings[1] + "&pw=" + strings[2] + "&email=" + strings[3] + "&ip=" + strings[4];
            }else if(sendMsg.equals("joinBaby")){ // 아기정보 등록
                sendMsg = "task=" + strings[0] + "&id=" + strings[1] + "&babyName=" + strings[2] + "&babyBirthday=" + strings[3];
            }

            osw.write(sendMsg);
            osw.flush();

            //jsp와 통신 성공 시 수행
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                // jsp에서 보낸 값을 받는 부분
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {
                // 통신 실패
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        //jsp로부터 받은 리턴 값
//        Log.v("return", Html.fromHtml(receiveMsg).toString().trim());

        return receiveMsg;
    }

}


