package com.myapp.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myapp.model.Feed;
import com.myapp.model.User;
import com.myapp.service.PostUserService;
import com.myapp.service.impl.PostServiceImpl;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PostHandler extends   BaseHandler{

    private final PostUserService postUserService = new PostServiceImpl();
    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (path.matches("/api/posts/\\d+")) { // Handle /api/{userid}/feed
            int userId = extractUserId(path);
            System.out.println(userId);
            List<Feed> feed = postUserService.getPost(userId);
            System.out.println("feed");
            System.out.println(feed);

            ObjectMapper objectMapper = new ObjectMapper();
            Object json = objectMapper.writeValueAsString(feed);
//            String jsonResponse = objectMapper.writeValueAsString(feed);
//            System.out.println(jsonResponse);

            setResponse(exchange,json.toString());
//            List<Feed> response = getFeeds(userId);
//            setResponse(exchange,response);
        }
    }


    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        System.out.println("Post message is being called handler");
        if (path.matches("/api/posts/")) {
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Parse fields from JSON body
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            Map<String, Object> jsonMap = gson.fromJson(requestBody, mapType);

            String content = (String) jsonMap.get("content");
            int userid = ((Double) jsonMap.get("user_id")).intValue();
            Feed feed = postUserService.addPost(1,userid,content);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(feed);
            setResponse(exchange,jsonResponse);
        }
    }
}
