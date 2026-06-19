package com.lld.patterns.structural.proxy.remote;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RemoteUserService implements RemoteUserServiceInterface {
    private final String baseUrl;
    private final Random random = new Random();

    public RemoteUserService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Map<String, Object> getUser(int id) throws Exception {
        System.out.println("[REMOTE] Fetching user " + id + " from " + this.baseUrl + "/users/" + id);
        
        // Simulate network delay
        Thread.sleep(500);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("name", "User " + id);
        response.put("email", "user" + id + "@example.com");
        response.put("createdAt", Instant.now().toString());
        return response;
    }

    @Override
    public Map<String, Object> createUser(Map<String, Object> userData) throws Exception {
        System.out.println("[REMOTE] Creating user on " + this.baseUrl + "/users");
        
        // Simulate network delay
        Thread.sleep(500);
        
        Map<String, Object> response = new HashMap<>(userData);
        response.put("id", random.nextInt(1000));
        response.put("createdAt", Instant.now().toString());
        return response;
    }
}
