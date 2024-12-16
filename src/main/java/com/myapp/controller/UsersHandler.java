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
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;
import com.myapp.service.impl.UserContext;
public class UsersHandler extends BaseHandler {

    private static final UserService userService = new UserServiceImpl();
    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.matches("/api/users/\\d+")) { // Handle /api/users (all users)
            int userId = extractUserId(path);
            System.out.println(userId);
            List<User> response = getAllUsers(userId);

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
            User user = userService.createUser(1,name);
            UserContext usercontext = new UserContext();
            usercontext.addUserService(user.getId(),userService);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(user);
            System.out.println("jsonResponsewdw" + jsonResponse);
            setResponse(exchange,jsonResponse);
        }



    }

    // Get all users
    private List<User> getAllUsers(int userId) {
        List<User> users = userService.getAllUsers(userId);
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


    protected void setResponse(HttpExchange exchange,String response) throws IOException {
        if (response != null) {
            sendJsonResponse(exchange, 200, response);
        } else {
            sendErrorResponse(exchange, 404, response);
        }
    }
}
