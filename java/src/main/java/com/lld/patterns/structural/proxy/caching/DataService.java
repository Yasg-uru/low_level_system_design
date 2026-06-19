package com.lld.patterns.structural.proxy.caching;

import java.util.Map;

public interface DataService {
    Map<String, Object> getData(String id) throws Exception;
}
