package com.example.aim_project;

public class DiaryVO {

    private String id; // 회원 아이디
    private String date; // 작성 날짜
    private String title; // 제목
    private String contents; // 내용

    public DiaryVO(String id, String date, String title, String contents) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.contents = contents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
