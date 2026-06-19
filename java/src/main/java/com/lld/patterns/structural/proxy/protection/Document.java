package com.lld.patterns.structural.proxy.protection;

public interface Document {
    void read(String content);
    void write(String content, String newContent);
    void delete(String content);
}
