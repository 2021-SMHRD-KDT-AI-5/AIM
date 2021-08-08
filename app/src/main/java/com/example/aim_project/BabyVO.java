package com.example.aim_project;

public class BabyVO {

    private String id; // 회원(부모님) 아아디
    private String name; // 아기 이름
    private String birthday; // 아기 생일

    public BabyVO(String id, String name, String birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
