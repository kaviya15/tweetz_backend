package com.myapp.service;

import com.myapp.model.Follower;
import com.myapp.model.User;

import java.util.List;

public interface FollowService {
    public Follower followUser(Follower follower);
    public String unfollowUser(Follower follower);
    public List<User> getFollowers(int user_id);
    public List<User>  getFollowing(int user_id);


}
