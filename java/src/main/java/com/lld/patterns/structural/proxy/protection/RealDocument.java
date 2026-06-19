package com.lld.patterns.structural.proxy.protection;

public class RealDocument implements Document {
    @Override
    public void read(String content) {
        System.out.println("Reading document: \"" + content + "\"");
    }

    @Override
    public void write(String content, String newContent) {
        System.out.println("Writing to document: \"" + content + "\" -> \"" + newContent + "\"");
    }

    @Override
    public void delete(String content) {
        System.out.println("Deleting document: \"" + content + "\"");
    }
}
