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
        server.createContext("/api/users", new UsersHandler());
        server.createContext("/api/posts", new PostHandler());
        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("Server started on port 8080");
    }
}