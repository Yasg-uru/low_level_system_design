package com.lld.patterns.structural.composite;

// Component Interface
public interface FileSystemNode {
    String getName();
    int getSize();
    void print(String indent);
}
