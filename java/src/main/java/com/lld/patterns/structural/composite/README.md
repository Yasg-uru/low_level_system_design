# Composite Pattern

## Overview
The Composite Pattern lets you compose objects into tree structures to represent part-whole hierarchies. It allows clients to treat individual objects and compositions of objects uniformly.

## When to Use
- When you want to represent part-whole hierarchies of objects
- When you want clients to ignore the difference between compositions of objects and individual objects
- When you want to treat individual objects and compositions uniformly
- When building tree structures like file systems, UI component trees, or organization charts

## Benefits
- **Uniformity**: Clients can treat individual objects and compositions uniformly
- **Simplicity**: Simplifies client code by not having to distinguish between leaf and composite objects
- **Flexibility**: Easy to add new types of components
- **Hierarchical Structure**: Naturally represents tree-like structures

## Example Scenario
A file system where files (leaf nodes) and directories (composite nodes) can be treated uniformly - both can have operations like `getSize()`, `getName()`, etc.

## Structure
```
Component (Interface)
    ↓
Leaf (Individual object)
Composite (Container of Components)
```

## Common Mistakes

❌ **Bad approach (Treating leaf and composite objects differently in client code)**
```java
// Client must check type and handle differently, leading to complex conditional logic
public void printFileSystem(Object node) {
    if (node instanceof File) {
        System.out.println(((File) node).getName());
    } else if (node instanceof Directory) {
        System.out.println(((Directory) node).getName() + "/");
        for (Object child : ((Directory) node).getChildren()) {
            printFileSystem(child);
        }
    }
}
```

✅ **Good approach (Using Composite Pattern for uniform treatment)**
```java
// Both files and directories implement the same interface. Client code is simplified.
public interface FileSystemNode {
    void print();
}

public class File implements FileSystemNode {
    private String name;
    public void print() {
        System.out.println(name);
    }
}

public class Directory implements FileSystemNode {
    private List<FileSystemNode> children = new ArrayList<>();
    private String name;

    public void add(FileSystemNode child) {
        children.add(child);
    }

    public void print() {
        System.out.println(name + "/");
        for (FileSystemNode child : children) {
            child.print();
        }
    }
}
```

## Key Points
- The Component interface defines operations for both leaf and composite objects
- Leaf objects implement the Component interface directly
- Composite objects store child Components and implement operations by delegating to children
- Client code works with the Component interface, unaware of whether it's a leaf or composite
- The pattern enables recursive operations on tree structures
