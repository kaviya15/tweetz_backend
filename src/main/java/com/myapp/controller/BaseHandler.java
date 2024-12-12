package com.myapp.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class BaseHandler implements HttpHandler {

    // Send JSON response (common functionality)
    protected void sendJsonResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    // Send error response (common functionality)
    protected void sendErrorResponse(HttpExchange exchange, int statusCode, String errorMessage) throws IOException {
        String response = String.format("{\"error\":\"%s\"}", errorMessage);
        sendJsonResponse(exchange, statusCode, response);
    }

    // Abstract methods to enforce structure
    protected abstract void handleGet(HttpExchange exchange) throws IOException;

    protected abstract void handlePost(HttpExchange exchange) throws IOException;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

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
