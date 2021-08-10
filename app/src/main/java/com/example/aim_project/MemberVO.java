package com.example.aim_project;

public class MemberVO {

    private String id; // 회원 아이디
    private String pw; // 비밀번호
    private String email; // 이메일
    private String ip; // IP캠 서버주소

    public MemberVO(String id, String pw, String email, String ip) {
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
