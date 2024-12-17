package com.myapp.service.impl;

import com.myapp.model.Notifications;
import com.myapp.model.User;
import com.myapp.repository.NotificationRepository;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    public String getUserById(int userId) {
        String name  = NotificationRepository.findUserById(userId);
        System.out.println(name + "from get user by id ");
       return name;
    }

    public List<Notifications> getNotifications(int user_id,String name){
        List<Notifications>  ll=  NotificationRepository.getUnreadNotifications(user_id, getUserById(user_id) );
        System.out.println("notificatiosn");
        System.out.println(ll);
        return  ll;
    }

    public  void markNotificationsAsRead(int user_id){
        NotificationRepository.markNotificationsAsRead(user_id);
    }

}
