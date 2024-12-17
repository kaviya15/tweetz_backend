package com.myapp.service.impl;

import com.myapp.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserContext {
    private static final Map<Integer, UserService> userServiceMap = new HashMap<>();


    public static void addUserService(int userId, UserService userService) {
        System.out.println("userServiceMap");
        System.out.println(userServiceMap);
        userServiceMap.put(userId, userService);
        System.out.println(userServiceMap);
    }

    public static UserService getUserService(int userId) {
        return userServiceMap.get(userId);
    }

    public static void removeUserService(int userId) {
        userServiceMap.remove(userId);
    }
}


