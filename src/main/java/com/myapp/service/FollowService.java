package com.myapp.service;

import com.myapp.model.User;

import java.util.List;

public interface FollowService {
    public void followUser(int follower_id, int followee_id);
    public void UnfollowUser(int follower_id, int followee_id);
    public List<User> getFollowers(int user_id);
    public List<User>  getFollowing(int user_id);


}
