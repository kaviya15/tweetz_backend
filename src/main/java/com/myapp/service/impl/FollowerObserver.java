package com.myapp.service.impl;

import com.myapp.model.Followers;
import com.myapp.repository.NotificationRepository;
import com.myapp.service.Observer;

public class FollowerObserver implements Observer {
    private final Followers follower;

    public FollowerObserver(Followers follower) {
        this.follower = follower;
    }

    // The update method will notify the follower through the service
    public void update(String message) {
        System.out.println("Notifying Follower ID: " + " -> " + this.follower.get_followee_id());
        System.out.println("Notifying Follower ID: " + " -> " + message);
        // You can implement additional notification logic here (e.g., email, push notifications)
        NotificationRepository.addNotification(this.follower.get_follower_id(), "New Notification received");
    }

}
