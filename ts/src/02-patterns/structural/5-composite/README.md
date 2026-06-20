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
```typescript
// Client must check type and handle differently, leading to complex conditional logic
function printFileSystem(node: any) {
  if (node.type === 'file') {
    console.log(node.name);
  } else if (node.type === 'directory') {
    console.log(node.name + '/');
    node.children.forEach(child => printFileSystem(child));
  }
}
```

✅ **Good approach (Using Composite Pattern for uniform treatment)**
```typescript
// Both files and directories implement the same interface. Client code is simplified.
interface FileSystemNode {
  print(): void;
}

class File implements FileSystemNode {
  constructor(private name: string) {}
  print(): void {
    console.log(this.name);
  }
}

class Directory implements FileSystemNode {
  private children: FileSystemNode[] = [];
  constructor(private name: string) {}

  add(child: FileSystemNode): void {
    this.children.push(child);
  }

  print(): void {
    console.log(this.name + '/');
    this.children.forEach(child => child.print());
  }
}
```

## Key Points
- The Component interface defines operations for both leaf and composite objects
- Leaf objects implement the Component interface directly
- Composite objects store child Components and implement operations by delegating to children
- Client code works with the Component interface, unaware of whether it's a leaf or composite
- The pattern enables recursive operations on tree structures
