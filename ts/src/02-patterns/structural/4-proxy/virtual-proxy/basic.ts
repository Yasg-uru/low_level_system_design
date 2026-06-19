// Virtual Proxy Example - Lazy Loading of Expensive Objects

// Subject Interface
interface Image {
  display(): void;
}

// Real Subject - Expensive to create
class RealImage implements Image {
  private filename: string;

  constructor(filename: string) {
    this.filename = filename;
    this.loadFromDisk();
  }

  private loadFromDisk(): void {
    console.log(`Loading ${this.filename} from disk... (expensive operation)`);
    // Simulate expensive operation
    const start = Date.now();
    while (Date.now() - start < 1000) {
      // Simulating delay
    }
    console.log(`${this.filename} loaded.`);
  }

  display(): void {
    console.log(`Displaying ${this.filename}`);
  }
}

// Virtual Proxy - Delays creation of RealImage
class ProxyImage implements Image {
  private filename: string;
  private realImage: RealImage | null = null;

  constructor(filename: string) {
    this.filename = filename;
  }

  display(): void {
    // RealImage is only created when first accessed
    if (this.realImage === null) {
      console.log("Creating RealImage on first access...");
      this.realImage = new RealImage(this.filename);
    }
    this.realImage.display();
  }
}

// Usage
function virtualProxyDemo(): void {
  console.log("=== Virtual Proxy Demo ===\n");

  console.log("Creating proxy (no expensive operation yet):");
  const image1 = new ProxyImage("photo1.jpg");
  const image2 = new ProxyImage("photo2.jpg");

  console.log("\nFirst display - triggers expensive loading:");
  image1.display();

  console.log("\nSecond display - uses cached object:");
  image1.display();

  console.log("\nDisplaying second image - triggers its loading:");
  image2.display();

  console.log("\nSecond image displayed again - uses cached object:");
  image2.display();
}

// Run the demo
virtualProxyDemo();
