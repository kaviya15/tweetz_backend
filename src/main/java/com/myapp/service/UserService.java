package com.myapp.service;

import com.myapp.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void createUser(int i, String name);
    User getUserById(int userId);
    List<String> getUserFeed(int userId);

}

