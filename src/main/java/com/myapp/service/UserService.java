package com.myapp.service;

import com.myapp.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void createUser(int id, String name);

    User getUserById(int userId);

    List<String> getUserFeed(int userId);

    List<User> getUserFollowers(int userId);

    List<User> getUserFollowing(int userId);

    void followUser(int user_id, int follower_id);

    void unfollowUser(int user_id, int follower_id);
}

