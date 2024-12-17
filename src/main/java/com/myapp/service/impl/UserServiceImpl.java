package com.myapp.service.impl;

import com.myapp.model.Followers;
import com.myapp.model.User;
import com.myapp.repository.UserRepository;
//import com.myapp.service.UserNotificationService;
import com.myapp.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService  {


    private final UserRepository userRepository = new UserRepository();
    private List<FollowerObserver> followersList = new ArrayList<>();
//    private int userId;


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
    public void removeObserver(FollowerObserver follower) {
        followersList.remove(follower);
    }


    @Override
    public void addObserver(FollowerObserver newfollower) {
        followersList.add(newfollower);
    }
    @Override
    public void notifyObservers(String message) {

        for(FollowerObserver ls : followersList){
                ls.update(message);
        }
    }
}


//
//    public  void removeObserver(Follower follower){
//
//    }
//

