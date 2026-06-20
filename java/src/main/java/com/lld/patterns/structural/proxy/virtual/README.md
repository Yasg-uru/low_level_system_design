# Virtual Proxy

## Overview
A Virtual Proxy is a proxy pattern that delays the creation of expensive objects until they are actually needed. This is also known as lazy initialization.

## When to Use
- When creating an object is expensive (time-consuming or resource-intensive)
- When the object might not be used at all
- When you want to defer initialization until the first access

## Benefits
- **Performance**: Avoids expensive object creation if the object is never used
- **Memory Efficiency**: Objects are only created when needed
- **Lazy Loading**: Resources are loaded on-demand

## Example Scenario
Loading a large image file - the image is only loaded when it needs to be displayed, not when the application starts.

## Structure
```
Subject (Interface)
    ↓
RealSubject (Actual expensive object)
    ↓
Proxy (Virtual Proxy - delays RealSubject creation)
```

## Common Mistakes

❌ **Bad approach (Eagerly loading expensive resources inside constructors)**
```java
// Constructing the Page instantly triggers massive disk/network reading of a 10MB image, even if never shown.
public class RealImage implements Image {
    public RealImage(String filename) {
        loadFromDisk(); // Slow operation blocking thread
    }
    public void display() { System.out.println("Displaying " + filename); }
}
```

✅ **Good approach (Using Virtual Proxy for On-Demand Creation)**
```java
// The image proxy is lightweight to construct. The real image is created only when display() is called.
public class ProxyImage implements Image {
    private RealImage realImage = null;
    private String filename;

    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}
```

## Key Points
- The proxy maintains a reference to the real subject
- The real subject is only instantiated when first accessed
- The proxy forwards requests to the real subject after creation
- Client code interacts with the proxy, unaware of the lazy loading
