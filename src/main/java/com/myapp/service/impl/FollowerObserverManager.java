package com.myapp.service.impl;

import com.myapp.model.Follower;
import com.myapp.repository.NotificationRepository;
import com.myapp.service.Observer;

import java.util.HashMap;
import java.util.Map;

public class FollowerObserverManager implements Observer {
    private final Map<Follower, String> followerNotifications = new HashMap<>();

    // Add a follower to the observer list
    public void addFollower(Follower follower) {
        followerNotifications.put(follower, ""); // Initialize with empty notification
    }

    // Remove a follower from the observer list
    public void removeFollower(Follower follower) {
        followerNotifications.remove(follower);
    }

    // Notify all followers with a message
    @Override
    public void update(String message) {
        for (Follower follower : followerNotifications.keySet()) {
             NotificationRepository.addNotification(follower.get_follower_id() , message);
//            sendNotification(follower, message);
        }
    }

    // Custom method to send notifications to a follower
//    private void sendNotification(Follower follower, String message) {
//        System.out.println("Notifying follower ID " + follower.get_follower_id() + ": " + message);
//        // Store the message for record-keeping (optional)
//        followerNotifications.put(follower, message);
//    }
}
