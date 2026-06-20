package com.lld.patterns.structural.composite;

import java.util.ArrayList;
import java.util.List;

// Composite - Directory that can contain files and other directories
public class Directory implements FileSystemNode {
    private List<FileSystemNode> children = new ArrayList<>();
    private String name;

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void add(FileSystemNode child) {
        children.add(child);
    }

    public void remove(FileSystemNode child) {
        children.remove(child);
    }

    @Override
    public int getSize() {
        int total = 0;
        for (FileSystemNode child : children) {
            total += child.getSize();
        }
        return total;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "📁 " + name + "/ (" + getSize() + " bytes)");
        for (FileSystemNode child : children) {
            child.print(indent + "  ");
        }
    }
}
