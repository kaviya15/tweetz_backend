package com.myapp.service.impl;

import com.myapp.model.Notifications;
import com.myapp.repository.NotificationRepository;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {


    public List<Notifications> getNotifications(int user_id,String name){
        List<Notifications>  ll=  NotificationRepository.getUnreadNotifications(user_id,name);
        System.out.println("notificatiosn");
        System.out.println(ll);
        return  ll;
    }

}
