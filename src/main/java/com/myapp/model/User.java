package com.myapp.model;

import java.util.HashSet;

public class User {
    private int id;
    private String name;
    private  HashSet<String> followers;
    private  HashSet<String> following;

    public User(int id, String name, HashSet<String> followers ,  HashSet<String> following) {
        this.id = id;
        this.name = name;
        this.followers = new HashSet<>();
        this.following = new HashSet<>();

    }
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

