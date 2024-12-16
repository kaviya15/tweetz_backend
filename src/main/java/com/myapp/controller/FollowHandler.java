package com.myapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.model.Followers;
import com.myapp.model.User;
import com.myapp.service.FollowService;
import com.myapp.service.impl.FollowerServiceImpl;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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


    protected void setResponse(HttpExchange exchange,List<User> response) throws IOException {
        if (response != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(response);
            sendJsonResponse(exchange, 200, jsonResponse);
        } else {
            sendErrorResponse(exchange, 404, "Error getting followers");
        }
    }
    // method overloading -- polymorphic concept
    protected void setResponse(HttpExchange exchange,String response) throws IOException {
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
        System.out.println("handle get invovked");
        if (path.matches("/api/following/\\d+/followers")) { // Handle /api/{userid}/followers
            int userId = extractUserId(path);
            System.out.println("userId");
            System.out.println(userId);
            List<User> response = getFollowers(userId);
            setResponse(exchange,response);
        }

        else if (path.matches("/api/following/\\d+/following")) { // Handle /api/{userid}/following
            int userId = extractUserId(path);
            List<User> response = getFollowing(userId);
            System.out.println("jsonResponsewdw" + response);
            setResponse(exchange,response);
        }
    }



    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();
        if("/api/following/unfollow".equals(path)){

            System.out.println("unfollow end point hit");
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            Map<String, Object> parsedValues = parseInput(requestBody);
            int follower_id = get_follower_id(parsedValues);
            int followee_id =get_followee_id(parsedValues);


            String response =   followService.unfollowUser(new Followers(1,follower_id,followee_id));
            setResponse(exchange,response);
        }


        else if("/api/following/follow".equals(path)){
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            Map<String, Object> parsedValues = parseInput(requestBody);

            int follower_id = get_follower_id(parsedValues);
            int followee_id =get_followee_id(parsedValues);
            Followers response =  followService.followUser(new Followers(1,follower_id,followee_id));
            System.out.println(response);
            setResponse(exchange,response.toString());

        }
    }
}
