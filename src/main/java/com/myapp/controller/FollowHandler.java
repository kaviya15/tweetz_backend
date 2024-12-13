package com.myapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myapp.model.User;
import com.myapp.service.FollowService;
import com.myapp.service.UserService;
import com.myapp.service.impl.FollowerServiceImpl;
import com.myapp.service.impl.UserServiceImpl;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FollowHandler extends  BaseHandler {

    private static final FollowService followService = new FollowerServiceImpl();


    private int get_follower_id(Map<String, Object> parsedValues){
        return  ((Double) parsedValues.get("follower_id")).intValue();
    }

    private int get_followee_id(Map<String, Object> parsedValues){
        return  ((Double) parsedValues.get("followee_id")).intValue();
    }



    private List<User> getFollowers(int userId) {
        // Fetch user followers from the service layer
        List<User> followers = followService.getFollowers(userId); // Add this method in UserService
        System.out.println(followers);
        if(followers!=null){
            return followers; // Use JSON serialization
        }
        return  null;

    }


    private List<User> getFollowing(int userId) {
        // Fetch user's following from the service layer
        List<User> following = followService.getFollowing(userId); // Add this method in UserService
        if(following!=null){
            return following; // Use JSON serialization
        }
        return null;

    }


    private void setResponse(HttpExchange exchange,List<User> response) throws IOException {
        if (response != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(response);
            sendJsonResponse(exchange, 200, jsonResponse);
        } else {
            sendErrorResponse(exchange, 404, "Error getting followers");
        }
    }


    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.matches("/api/\\d+/followers")) { // Handle /api/{userid}/followers
            int userId = extractUserId(path);
            List<User> response = getFollowers(userId);
            setResponse(exchange,response);
        }

        else if (path.matches("/api/\\d+/following")) { // Handle /api/{userid}/following
            int userId = extractUserId(path);
            List<User> response = getFollowing(userId);
            setResponse(exchange,response);
        }



    }






    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();
        if("/api/users/unfollow".equals(path)){
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            Map<String, Object> parsedValues = parseInput(requestBody);
            // Parse fields from JSON body
//            String user_id_string = requestBody.split("\"user_id\":")[1].split(",")[0].trim();
//            String follower_id_string = requestBody.split("\"follower_id\":")[1].split(",")[0].trim();


            int follower_id = get_follower_id(parsedValues);
            int followee_id =get_followee_id(parsedValues);


//            userService.unfollowUser(user_id, follower_id);
        }


        else if("/api/users/follow".equals(path)){
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            Map<String, Object> parsedValues = parseInput(requestBody);


            int follower_id = get_follower_id(parsedValues);
            int followee_id =get_followee_id(parsedValues);



            // Parse fields from JSON body
//            String user_id_string = requestBody.split("\"user_id\":")[1].split(",")[0].trim();
//            String follower_id_string = requestBody.split("\"follower_id\":")[1].split(",")[0].trim();
//            int user_id = Integer.parseInt(user_id_string);
//            int follower_id = Integer.parseInt(follower_id_string);
//            userService.followUser(user_id, follower_id);

        }
    }
}
