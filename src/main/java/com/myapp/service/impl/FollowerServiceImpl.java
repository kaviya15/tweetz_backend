package com.myapp.service.impl;

import com.myapp.model.User;
import com.myapp.repository.FollowRepository;
import com.myapp.repository.UserRepository;
import com.myapp.service.FollowService;

import java.util.List;

public class FollowerServiceImpl implements FollowService {

    private final FollowRepository followRepository = new FollowRepository();
    @Override
    public void followUser(int user_id, int follower_id) {

    }

    @Override
    public void UnfollowUser(int user_id, int follower_id) {

    }

    @Override
    public List<User> getFollowers(int user_id) {
        return followRepository.getFollowers(user_id);
    }

    @Override
    public List<User> getFollowing(int user_id) {
        return followRepository.getFollowing(user_id);

    }


}
