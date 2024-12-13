package com.myapp.service.impl;

import com.myapp.model.User;
import com.myapp.repository.UserRepository;
import com.myapp.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepository();

    @Override
    public List<User> getAllUsers(int userid) {
        return userRepository.findAll(userid);
    }

    @Override
    public User createUser(int id, String name) {
       return userRepository.save(new User(id, name));
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public List<String> getUserFeed(int userId) {
        System.out.println(userId);
        return null;
    }


}

