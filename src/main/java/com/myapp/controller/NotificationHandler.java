package com.myapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.model.Notifications;
import com.myapp.service.PostUserService;
import com.myapp.service.impl.NotificationService;
import com.myapp.service.impl.PostServiceImpl;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotificationHandler extends BaseHandler {
    private final NotificationService notificationService = new NotificationService();

    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {

    }

    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        System.out.println(path);
        if (path.matches("/api/notifications/")) {
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            Map<String, Object> parsedValues = parseInput(requestBody);
            System.out.println(parsedValues);
            int user_id = ((Double) parsedValues.get("user_id")).intValue();
            String user_name =(String) parsedValues.get("user_name");
            System.out.println(user_id);
            System.out.println(user_name);
            System.out.println("uhjkhjkhjhjkh");
             List<Notifications> notificationsList =  notificationService.getNotifications(user_id,user_name);
             System.out.println(notificationsList);
            ObjectMapper objectMapper = new ObjectMapper();
            Object json = objectMapper.writeValueAsString(notificationsList);
            setResponse(exchange,json.toString());

        }
    }
}
