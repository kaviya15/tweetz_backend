package com.myapp.service;

import com.myapp.model.Followers;
import com.myapp.model.User;

import java.util.List;

public interface FollowService {
    public Followers followUser(Followers follower);
    public String unfollowUser(Followers follower);
    public List<User> getFollowers(int user_id);
    public List<User>  getFollowing(int user_id);


}
