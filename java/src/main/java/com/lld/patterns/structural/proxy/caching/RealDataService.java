package com.lld.patterns.structural.proxy.caching;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RealDataService implements DataService {
    private final Random random = new Random();

    @Override
    public Map<String, Object> getData(String id) throws Exception {
        System.out.println("Fetching data for ID: " + id + " from database... (expensive operation)");
        
        // Simulate expensive database call
        Thread.sleep(1000);
        
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("name", "Item " + id);
        data.put("value", random.nextInt(100));
        data.put("timestamp", Instant.now().toString());
        
        System.out.println("Data fetched for ID: " + id);
        return data;
    }
}
