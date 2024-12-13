package com.myapp.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

public class UsersHandler extends BaseHandler {

    private static final UserService userService = new UserServiceImpl();
    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        System.out.println(path.matches("/api/users/\\d+/feed"));
        if (path.matches("/api/users/\\d+")) { // Handle /api/users/{userid}
            int userId = extractUserId(path);
            String response = getUserById(userId);
            setResponse(exchange,response);
        } else if ("/api/users".equals(path)) { // Handle /api/users (all users)
            List<User> response = getAllUsers();
//            User newis = new User(1, "Kaviya");
//            System.out.println("asdasdasd" + newis);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(response);
            System.out.println("jsonResponsewdw" + jsonResponse);
            setResponse(exchange,jsonResponse);
        } else {
            sendErrorResponse(exchange, 404, "Endpoint not found");
        }
    }

    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        System.out.println(path);
        if("/api/users".equals(path)){
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Parse fields from JSON body

            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            Map<String, Object> jsonMap = gson.fromJson(requestBody, mapType);

            // Access fields dynamically
//            int id = ((Double) jsonMap.get("id")).intValue(); // Gson parses numbers as Double
            String name = (String) jsonMap.get("name");

            System.out.println(name);
            /* CHECK IF THE USER ALREADY CREATED  **/
            userService.createUser(1,name);

            sendJsonResponse(exchange, 201, "{\"status\":\"User created\"}");
        }



    }

    // Get all users
    private List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users; // Use JSON serialization in real scenarios
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




    // Helper method to extract user ID from the path


    private void setResponse(HttpExchange exchange,String response) throws IOException {
        if (response != null) {
            sendJsonResponse(exchange, 200, response);
        } else {
            sendErrorResponse(exchange, 404, response);
        }
    }
}
