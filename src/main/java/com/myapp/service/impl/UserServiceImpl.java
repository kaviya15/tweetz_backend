package com.myapp.service.impl;

import com.myapp.model.Follower;
import com.myapp.model.User;
import com.myapp.repository.UserRepository;
//import com.myapp.service.UserNotificationService;
import com.myapp.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService  {
    private final FollowerObserverManager observerManager = new FollowerObserverManager();

    private final UserRepository userRepository = new UserRepository();
    private List<Follower> followersList = new ArrayList<>();
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
    public List<String> getUserFeed(int userId) {
        System.out.println(userId);
        return null;
    }

    @Override
    public void notifyObservers(String message) {
        observerManager.update(message); // Notify all followers
    }

    public void addObserver(Follower follower) {
        observerManager.addFollower(follower); // Add follower to observer manager
    }

    public void removeObserver(Follower follower) {
        observerManager.removeFollower(follower); // Remove follower from observer manager
    }



//    public  void addObserver(Follower follower){
//        /** make db call to get the followers for loop  */
//        /* get following */
//        followersList.add(follower);
//
//
//
//    }
//
//    public  void removeObserver(Follower follower){
//
//    }
//
//    @Override
//    public void notifyObservers(String message) {
//        /* get the list of followers*/
//        //for (Follower follower : followers) {
////        follower.update(message); // Notify each follower
////        }
//
//        for(int ls : followersList){
//
//        }
//    }
}

