package com.lld.patterns.structural.composite;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Composite Pattern Demo ===\n");

        // Create individual files (leaf nodes)
        FileSystemFile file1 = new FileSystemFile("document.txt", 1024);
        FileSystemFile file2 = new FileSystemFile("image.jpg", 2048);
        FileSystemFile file3 = new FileSystemFile("script.js", 512);
        FileSystemFile file4 = new FileSystemFile("style.css", 256);
        FileSystemFile file5 = new FileSystemFile("readme.md", 128);

        System.out.println("--- Creating file system structure ---");

        // Create directories (composite nodes)
        Directory srcDir = new Directory("src");
        Directory assetsDir = new Directory("assets");
        Directory projectDir = new Directory("my-project");

        // Build the hierarchy
        srcDir.add(file3);  // script.js
        srcDir.add(file4);  // style.css

        assetsDir.add(file2); // image.jpg

        projectDir.add(file1);  // document.txt
        projectDir.add(srcDir); // src/ directory
        projectDir.add(assetsDir); // assets/ directory
        projectDir.add(file5);  // readme.md

        System.out.println("\n--- Printing file system tree ---");
        projectDir.print("");

        System.out.println("\n--- Getting sizes ---");
        System.out.println("Size of " + file1.getName() + ": " + file1.getSize() + " bytes");
        System.out.println("Size of " + srcDir.getName() + ": " + srcDir.getSize() + " bytes");
        System.out.println("Size of " + assetsDir.getName() + ": " + assetsDir.getSize() + " bytes");
        System.out.println("Size of " + projectDir.getName() + ": " + projectDir.getSize() + " bytes");

        System.out.println("\n--- Adding nested directory ---");
        Directory componentsDir = new Directory("components");
        FileSystemFile componentFile = new FileSystemFile("header.tsx", 768);
        componentsDir.add(componentFile);
        srcDir.add(componentsDir);

        System.out.println("\n--- Updated file system tree ---");
        projectDir.print("");

        System.out.println("\n--- Removing a file ---");
        srcDir.remove(file3);
        System.out.println("\n--- File system after removal ---");
        projectDir.print("");
    }
}
