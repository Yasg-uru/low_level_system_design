package com.lld.patterns.creational.builder;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Builder Design Pattern Active ===");

        // Example 1: Creating an immutable request configuration via explicit build step
        HttpRequest getProfileRequest = new HttpRequest.Builder()
                .setUrl("https://api.sandbox.internal/v1/users/me")
                .setMethod(HttpMethod.GET)
                .withBearerToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                .withRetries(3, 500)
                .setTimeout(5000)
                .build();

        System.out.println("\n-> Product built successfully. Attempting structural dispatch execution...");
        Response response1 = getProfileRequest.send();
        System.out.println("Response Received: " + response1);

        // Example 2: In-line convenience chaining utilizing short-circuit .send() termination
        System.out.println("\n-> Executing immediate transaction payload stream using chained handlers...");
        Map<String, Object> payload = new HashMap<>();
        payload.put("orderId", "ORD_99821");
        payload.put("totalAmount", 7500);

        Response response2 = new HttpRequest.Builder()
                .setUrl("https://api.sandbox.internal/v1/payments/transact")
                .setMethod(HttpMethod.POST)
                .withApiKey("sk_live_51NxB8", "X-Secure-Provider-Token")
                .setBody(payload)
                .noRedirects()
                .send();
                
        System.out.println("Response Received: " + response2);
    }
}
