package com.myapp.service;

import com.myapp.model.Feed;

import java.util.List;

public interface PostUserService {

    Feed addPost(int id,int user_id , String content);
    List<Feed> getPost(int user_id);
}
