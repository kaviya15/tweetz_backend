package com.myapp.service.impl;

import com.myapp.model.Follower;
import com.myapp.model.User;
import com.myapp.repository.FollowRepository;
import com.myapp.repository.UserRepository;
import com.myapp.service.FollowService;
import com.myapp.service.UserService;

import java.util.List;

public class FollowerServiceImpl implements FollowService {

    private final FollowRepository followRepository = new FollowRepository();
    @Override
    public Follower followUser(Follower follower) {
        System.out.println("follow user called");
        Follower newfollower = followRepository.followUser(follower);
        System.out.println(newfollower.get_followee_id());
        UserService userService = UserContext.getUserService(newfollower.get_followee_id());
        userService.addObserver(newfollower);
        return newfollower;

    }

    @Override
    public String  unfollowUser(Follower follower) {
       return followRepository.unfollowUser(follower);
    }

    @Override
    public List<User> getFollowers(int user_id) {
        return followRepository.getFollowers(user_id);
    }

    @Override
    public List<User> getFollowing(int user_id) {
        return followRepository.getFollowing(user_id);

    }

//    @Override
//    public String update(String message) {
//        return null;
//    }


}
