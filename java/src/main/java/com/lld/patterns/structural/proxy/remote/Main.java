package com.lld.patterns.structural.proxy.remote;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Remote Proxy Demo ===");
        try {
            UserServiceProxy userService = new UserServiceProxy("https://api.example.com");

            System.out.println("--- Fetching user from remote server ---");
            Map<String, Object> user1 = userService.getUser(1);
            System.out.println("Received user: " + user1);

            System.out.println("\n--- Fetching another user ---");
            Map<String, Object> user2 = userService.getUser(2);
            System.out.println("Received user: " + user2);

            System.out.println("\n--- Creating new user on remote server ---");
            Map<String, Object> userData = new HashMap<>();
            userData.put("name", "John Doe");
            userData.put("email", "john.doe@example.com");
            Map<String, Object> newUser = userService.createUser(userData);
            System.out.println("Created user: " + newUser);

            System.out.println("\n--- Total requests made: " + userService.getRequestCount() + " ---");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
