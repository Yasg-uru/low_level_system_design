package com.lld.patterns.structural.proxy.caching;

import java.util.HashMap;
import java.util.Map;

public class CachingDataServiceProxy implements DataService {
    private final RealDataService realService;
    private final Map<String, Map<String, Object>> cache;

    public CachingDataServiceProxy(RealDataService realService) {
        this.realService = realService;
        this.cache = new HashMap<>();
    }

    @Override
    public Map<String, Object> getData(String id) throws Exception {
        if (cache.containsKey(id)) {
            System.out.println("Cache HIT for ID: " + id + " - returning cached data");
            return cache.get(id);
        }

        System.out.println("Cache MISS for ID: " + id + " - fetching from real service");
        Map<String, Object> data = realService.getData(id);
        cache.put(id, data);
        System.out.println("Data cached for ID: " + id);
        return data;
    }

    public void clearCache() {
        cache.clear();
        System.out.println("Cache cleared");
    }

    public Map<String, Object> getCacheStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("size", cache.size());
        stats.put("keys", cache.keySet());
        return stats;
    }
}
