package com.example.aim_project;

import java.util.Random;

public class Tips {
    // DB 사용하지 않고 팁 출력하는 메소드
    public static String getTip(){
        Random rd = new Random();

        int cnt = 0;

        String[] tips = new String[2];

        tips[0] = "아기는 보통 생후 3~5개월 사이에 뒤집기를 시작합니다!";
        tips[1] = "아기는 보통 2~3개월 사이에 옹알이를 시작합니다!";

        return tips[rd.nextInt(tips.length)];
    }
}
