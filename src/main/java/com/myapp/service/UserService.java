package com.myapp.service;

import com.myapp.model.Followers;
import com.myapp.model.User;
import com.myapp.service.impl.FollowerObserver;

import java.util.List;

public interface UserService {
    List<User> getAllUsers(int userid);
    User createUser(int i, String name);
    User getUserById(int userId);

    void notifyObservers(String message);

    void addObserver(FollowerObserver newfollower);
    void removeObserver(FollowerObserver newfollower);
}

