package com.myapp.controller;
import com.myapp.model.Feed;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.util.ArrayList;

public class PostHandler extends   BaseHandler{

    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
//        String path = exchange.getRequestURI().getPath();
//        if (path.matches("/api/\\d+/feed")) { // Handle /api/{userid}/feed
//            int userId = extractUserId(path);
//            System.out.println(userId);
//            List<Feed> response = getFeeds(userId);
//            setResponse(exchange,response);
//        }
    }

//    private List<Feed> getFeeds(int userId) {
//        return new List<Feed>();
//    }

    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {

    }
}
