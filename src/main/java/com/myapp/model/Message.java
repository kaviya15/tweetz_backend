package com.myapp.model;

public class Message {
    private int id;
    private String content;
    private int userId;

    public Message(int id, String content, int userId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                '}';
    }
}

