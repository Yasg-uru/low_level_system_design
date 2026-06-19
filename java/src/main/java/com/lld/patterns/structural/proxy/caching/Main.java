package com.lld.patterns.structural.proxy.caching;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Caching Proxy Demo ===");
        try {
            RealDataService realService = new RealDataService();
            CachingDataServiceProxy proxyService = new CachingDataServiceProxy(realService);

            System.out.println("First request - will fetch from database:");
            proxyService.getData("item1");

            System.out.println("\nSecond request (same ID) - will use cache:");
            proxyService.getData("item1");

            System.out.println("\nThird request (different ID) - will fetch from database:");
            proxyService.getData("item2");

            System.out.println("\nFourth request (item1 again) - will use cache:");
            proxyService.getData("item1");

            System.out.println("\nCache stats:");
            System.out.println(proxyService.getCacheStats());

            System.out.println("\nClearing cache and making another request:");
            proxyService.clearCache();
            proxyService.getData("item1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
