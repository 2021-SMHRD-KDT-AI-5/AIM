package com.example.aim_project;

public class AlarmVO {

    private String id; // 회원 아이디
    private String babyName; // 아기 이름
    private String date; // 알람 울린 시간
    private String cry_move; // 우는건지 움직임이 이상한지 여부
//    private String contents; // 알람 내용

    public AlarmVO(String id, String babyName, String date, String cry_move) {
        this.id = id;
        this.babyName = babyName;
        this.date = date;
        this.cry_move = cry_move;
//        this.contents = contents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCry_move() {
        return cry_move;
    }

    public void setCry_move(String cry_move) {
        this.cry_move = cry_move;
    }

//    public String getContents() {
//        return contents;
//    }

//    public void setContents(String contents) {
//        this.contents = contents;
//    }
}
