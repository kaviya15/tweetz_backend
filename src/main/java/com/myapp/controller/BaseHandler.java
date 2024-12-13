package com.myapp.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class BaseHandler implements HttpHandler {

    // Send JSON response (common functionality)
    protected void addCorsHeaders(HttpExchange exchange) {
        if (!exchange.getResponseHeaders().containsKey("Access-Control-Allow-Origin")) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); // Allow all origins
        }
        if (!exchange.getResponseHeaders().containsKey("Access-Control-Allow-Methods")) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS"); // Allow specific methods
        }
        if (!exchange.getResponseHeaders().containsKey("Access-Control-Allow-Headers")) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*"); // Allow specific headers
        }
        if (!exchange.getResponseHeaders().containsKey("Access-Control-Max-Age")) {
            exchange.getResponseHeaders().add("Access-Control-Max-Age", "3600"); // Cache preflight response for 1 hour
        }
    }

    protected void sendJsonResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        addCorsHeaders(exchange);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    // Send error response (common functionality)
    protected void sendErrorResponse(HttpExchange exchange, int statusCode, String errorMessage) throws IOException {
        addCorsHeaders(exchange);
        String response = String.format("{\"error\":\"%s\"}", errorMessage);
        sendJsonResponse(exchange, statusCode, response);
    }
    protected void setResponse(HttpExchange exchange,String response) throws IOException {
        if (response != null) {
            sendJsonResponse(exchange, 200, response);
        } else {
            sendErrorResponse(exchange, 404, response);
        }
    }
    // Abstract methods to enforce structure
    protected abstract void handleGet(HttpExchange exchange) throws IOException;

    protected abstract void handlePost(HttpExchange exchange) throws IOException;

    protected Map<String, Object> parseInput(String requestBody){
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> jsonMap = gson.fromJson(requestBody, mapType);
        return jsonMap;
    }

    protected int extractUserId(String path) {
        return Integer.parseInt(path.split("/")[3]);
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        addCorsHeaders(exchange);

        // Handle the preflight OPTIONS request
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            // Respond with status 204 (No Content) for preflight
            exchange.sendResponseHeaders(204, -1); // -1 indicates no content
            return;
        }

        switch (method) {
            case "GET":
                handleGet(exchange);
                break;
            case "POST":
                handlePost(exchange);
                break;
            default:
                sendErrorResponse(exchange, 405, "Method not allowed");
                break;
        }
    }
}
