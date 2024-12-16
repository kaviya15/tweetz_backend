package com.myapp.service.impl;

import com.myapp.model.Feed;
import com.myapp.repository.PostRepository;
import com.myapp.service.PostUserService;
import com.myapp.service.UserService;

import java.util.List;

public class PostServiceImpl implements PostUserService {
    private  final PostRepository postRepository = new  PostRepository();



    @Override
    public Feed addPost(int id, int user_id, String content) {
        System.out.println("Post message is being called service");
        Feed feed =  postRepository.addPost(new Feed(1,user_id,content));
        UserService userService = UserContext.getUserService(user_id);
        userService.notifyObservers(content);
        /* call user repo  */
       return feed;

    }

    @Override
    public List<Feed> getPost(int user_id) {
        List<Feed> feeds = postRepository.getPost(user_id);
        System.out.println(feeds);
        return  feeds;
    }
}
