package com.myapp.service;

import com.myapp.model.Follower;
import com.myapp.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers(int userid);
    User createUser(int i, String name);
    User getUserById(int userId);
    List<String> getUserFeed(int userId);

    void notifyObservers(String message);

    void addObserver(Follower newfollower);
}

