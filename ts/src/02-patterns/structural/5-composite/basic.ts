// Composite Pattern Example - File System with Files and Directories

// Component Interface
interface FileSystemNode {
  getName(): string;
  getSize(): number;
  print(indent?: string): void;
}

// Leaf - Individual file
class FileSystemFile implements FileSystemNode {
  constructor(private name: string, private size: number) {}

  getName(): string {
    return this.name;
  }

  getSize(): number {
    return this.size;
  }

  print(indent: string = ""): void {
    console.log(`${indent}📄 ${this.name} (${this.size} bytes)`);
  }
}

// Composite - Directory that can contain files and other directories
class Directory implements FileSystemNode {
  private children: FileSystemNode[] = [];

  constructor(private name: string) {}

  getName(): string {
    return this.name;
  }

  add(child: FileSystemNode): void {
    this.children.push(child);
  }

  remove(child: FileSystemNode): void {
    const index = this.children.indexOf(child);
    if (index > -1) {
      this.children.splice(index, 1);
    }
  }

  getSize(): number {
    return this.children.reduce((total, child) => total + child.getSize(), 0);
  }

  print(indent: string = ""): void {
    console.log(`${indent}📁 ${this.name}/ (${this.getSize()} bytes)`);
    this.children.forEach(child => child.print(indent + "  "));
  }
}

// Usage
function compositePatternDemo(): void {
  console.log("=== Composite Pattern Demo ===\n");

  // Create individual files (leaf nodes)
  const file1 = new FileSystemFile("document.txt", 1024);
  const file2 = new FileSystemFile("image.jpg", 2048);
  const file3 = new FileSystemFile("script.js", 512);
  const file4 = new FileSystemFile("style.css", 256);
  const file5 = new FileSystemFile("readme.md", 128);

  console.log("--- Creating file system structure ---");

  // Create directories (composite nodes)
  const srcDir = new Directory("src");
  const assetsDir = new Directory("assets");
  const projectDir = new Directory("my-project");

  // Build the hierarchy
  srcDir.add(file3);  // script.js
  srcDir.add(file4);  // style.css

  assetsDir.add(file2); // image.jpg

  projectDir.add(file1);  // document.txt
  projectDir.add(srcDir); // src/ directory
  projectDir.add(assetsDir); // assets/ directory
  projectDir.add(file5);  // readme.md

  console.log("\n--- Printing file system tree ---");
  projectDir.print();

  console.log("\n--- Getting sizes ---");
  console.log(`Size of ${file1.getName()}: ${file1.getSize()} bytes`);
  console.log(`Size of ${srcDir.getName()}: ${srcDir.getSize()} bytes`);
  console.log(`Size of ${assetsDir.getName()}: ${assetsDir.getSize()} bytes`);
  console.log(`Size of ${projectDir.getName()}: ${projectDir.getSize()} bytes`);

  console.log("\n--- Adding nested directory ---");
  const componentsDir = new Directory("components");
  const componentFile = new FileSystemFile("header.tsx", 768);
  componentsDir.add(componentFile);
  srcDir.add(componentsDir);

  console.log("\n--- Updated file system tree ---");
  projectDir.print();

  console.log("\n--- Removing a file ---");
  srcDir.remove(file3);
  console.log("\n--- File system after removal ---");
  projectDir.print();
}

// Run the demo
compositePatternDemo();
