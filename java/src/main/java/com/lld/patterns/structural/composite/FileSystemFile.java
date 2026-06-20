package com.lld.patterns.structural.composite;

// Leaf - Individual file
public class FileSystemFile implements FileSystemNode {
    private String name;
    private int size;

    public FileSystemFile(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "📄 " + name + " (" + size + " bytes)");
    }
}
