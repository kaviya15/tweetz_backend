package com.myapp.model;

public class Feed {
    private int id;
    private String content;
    private int userId;

    private String name;
    private String timestamp;
    private boolean isDetailed = false;
    public Feed(int id, int userId,String content, ) {
        System.out.println("first constructor called");
        this.id = id;
        this.content = content;
        this.userId = userId;
    }
    public Feed(String timestamp,String content,String name) {
        System.out.println("Second constructor called");
        this.content = content;
        this.timestamp = timestamp;
        this.name = name;
        this.isDetailed = true;
    }


    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getUserId() {
        return userId;
    }

    public String toString() {
        if (isDetailed) {
            return "Feed{" +
                    "timestamp='" + timestamp + '\'' +
                    ", content='" + content + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        } else {
            return "Feed{" +
                    "id=" + id +
                    ", content='" + content + '\'' +
                    ", userId=" + userId +
                    '}';
        }
    }
}

