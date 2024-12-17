package com.myapp.controller;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;


public class BackendServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        BaseHandler userhandler = new UsersHandler();
        BaseHandler followhandler = new FollowHandler();
        BaseHandler notificationhandler = new NotificationHandler();
        BaseHandler posthandler = new PostHandler();
        server.createContext("/api/users", userhandler);
        server.createContext("/api/notifications", notificationhandler);
        server.createContext("/api/following",followhandler);
        server.createContext("/api/posts", posthandler);
        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("Server started on port 8080");
    }
}