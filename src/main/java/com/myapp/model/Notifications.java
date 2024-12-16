package com.myapp.model;

public class Notifications {
    private int user_id;
    private  String name;
    private String content;
    public Notifications(int user_id,String name, String content){
        this.user_id = user_id;
        this.content = content;
        this.name = name;
    }

    public int get_user_id(){
        return this.user_id;
    }

    public String getContent() {
        return content;
    }
    public  String getName(){
        return name;
    }
}
