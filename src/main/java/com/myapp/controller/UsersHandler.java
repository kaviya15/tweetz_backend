package com.myapp.controller;

import com.myapp.model.User;
import com.myapp.service.UserService;
import com.myapp.service.impl.UserServiceImpl;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class UsersHandler extends BaseHandler {

    private static final UserService userService = new UserServiceImpl();

    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        System.out.println(path.matches("/api/users/\\d+/feed"));
        if (path.matches("/api/users/\\d+/feed")) { // Handle /api/users/{userid}/feed
            int userId = extractUserId(path);
            System.out.println(userId);
            String response = getUserFeed(userId);
            setResponse(exchange,response);
        } else if (path.matches("/api/users/\\d+/followers")) { // Handle /api/users/{userid}/followers
            int userId = extractUserId(path);
            String response = getUserFollowers(userId);
            setResponse(exchange,response);
        } else if (path.matches("/api/users/\\d+/following")) { // Handle /api/users/{userid}/following
            int userId = extractUserId(path);
            String response = getUserFollowing(userId);
            setResponse(exchange,response);
        } else if (path.matches("/api/users/\\d+")) { // Handle /api/users/{userid}
            int userId = extractUserId(path);
            String response = getUserById(userId);
            setResponse(exchange,response);
        } else if ("/api/users".equals(path)) { // Handle /api/users (all users)
            String response = getAllUsers();
            setResponse(exchange,response);
        } else {
            sendErrorResponse(exchange, 404, "Endpoint not found");
        }
    }

    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if("/api/users".equals(path)){
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Parse fields from JSON body
            String idString = requestBody.split("\"id\":")[1].split(",")[0].trim();
            String name = requestBody.split("\"name\":")[1].split("}")[0].trim().replace("\"", "");
            int id = Integer.parseInt(idString);

            userService.createUser(id, name);

            sendJsonResponse(exchange, 201, "{\"status\":\"User created\"}");
        }
        else if("/api/users/follow".equals(path)){
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Parse fields from JSON body
            String user_id_string = requestBody.split("\"user_id\":")[1].split(",")[0].trim();
            String follower_id_string = requestBody.split("\"follower_id\":")[1].split(",")[0].trim();
            int user_id = Integer.parseInt(user_id_string);
            int follower_id = Integer.parseInt(follower_id_string);
            userService.followUser(user_id, follower_id);
        }
        else if("/api/users/unfollow".equals(path)){
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Parse fields from JSON body
            String user_id_string = requestBody.split("\"user_id\":")[1].split(",")[0].trim();
            String follower_id_string = requestBody.split("\"follower_id\":")[1].split(",")[0].trim();
            int user_id = Integer.parseInt(user_id_string);
            int follower_id = Integer.parseInt(follower_id_string);
            userService.unfollowUser(user_id, follower_id);
        }

    }

    // Get all users
    private String getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.toString(); // Use JSON serialization in real scenarios
    }

    // Get user by ID
    private String getUserById(int userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return user.toString(); // Use JSON serialization
        }
        return null;
    }

    // Get user's feed
    private String getUserFeed(int userId) {
        // Fetch user feed data from the service layer
        List<String> feed = userService.getUserFeed(userId); // Add this method in UserService
        if(feed !=null){
            return feed.toString(); // Use JSON serialization
        }
        return null;

    }

    // Get user's followers
    private String getUserFollowers(int userId) {
        // Fetch user followers from the service layer
        List<User> followers = userService.getUserFollowers(userId); // Add this method in UserService
        System.out.println(followers);
        if(followers!=null){
            return followers.toString(); // Use JSON serialization
        }
        return  null;

    }

    // Get user's following
    private String getUserFollowing(int userId) {
        // Fetch user's following from the service layer
        List<User> following = userService.getUserFollowing(userId); // Add this method in UserService
        if(following!=null){
            return following.toString(); // Use JSON serialization
        }
        return null;

    }

    // Helper method to extract user ID from the path
    private int extractUserId(String path) {
        return Integer.parseInt(path.split("/")[3]);
    }

    private void setResponse(HttpExchange exchange,String response) throws IOException {
        if (response != null) {
            sendJsonResponse(exchange, 200, response);
        } else {
            sendErrorResponse(exchange, 404, response);
        }
    }
}
