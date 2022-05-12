package com.example.auth;

public class User_data {
    private String email;
    private String pass;
    private int score=0;
    private String uid;
    private String key;
    public User_data(){}
    public User_data(String email, String pass){
        this.email=email;
        this.pass=pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public int getScore(){return score;}
    public void setScore(int score){this.score=score;}
    public String getUid(){return uid;}
    public void setUid(String uid){this.uid=uid;}
    public String getKey(){return uid;}
    public void setKey(String key){this.key=key;}
}
