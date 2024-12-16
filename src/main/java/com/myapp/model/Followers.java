package com.myapp.model;

public class Followers {

    private int id;
    private int follower_id;
    private int followee_id;

    public Followers(int id, int follower_id , int followee_id){
        this.id = id;
        this.follower_id = follower_id;
        this.followee_id = followee_id;
    }

    public int get_follower_id() {
        return  this.follower_id;
    }
    public int get_followee_id() {
        return  this.followee_id;
    }



}
