/**
 * Polymorphism through Method Overriding
 *
 * Child classes provide specific implementations of parent methods.
 * Enables writing code that works with parent type but uses child implementations.
 */

interface Shape {
  calculateArea(): number;
  calculatePerimeter(): number;
  getDescription(): string;
}

class Circle implements Shape {
  private radius: number;

  constructor(radius: number) {
    this.radius = radius;
  }

  calculateArea(): number {
    return Math.PI * this.radius * this.radius;
  }

  calculatePerimeter(): number {
    return 2 * Math.PI * this.radius;
  }

  getDescription(): string {
    return `Circle with radius ${this.radius}`;
  }
}

class Rectangle implements Shape {
  private width: number;
  private height: number;

  constructor(width: number, height: number) {
    this.width = width;
    this.height = height;
  }

  calculateArea(): number {
    return this.width * this.height;
  }

  calculatePerimeter(): number {
    return 2 * (this.width + this.height);
  }

  getDescription(): string {
    return `Rectangle ${this.width}x${this.height}`;
  }
}

class Triangle implements Shape {
  private sideA: number;
  private sideB: number;
  private sideC: number;

  constructor(sideA: number, sideB: number, sideC: number) {
    this.sideA = sideA;
    this.sideB = sideB;
    this.sideC = sideC;
  }

  calculateArea(): number {
    const s = (this.sideA + this.sideB + this.sideC) / 2;
    return Math.sqrt(s * (s - this.sideA) * (s - this.sideB) * (s - this.sideC));
  }

  calculatePerimeter(): number {
    return this.sideA + this.sideB + this.sideC;
  }

  getDescription(): string {
    return `Triangle ${this.sideA}-${this.sideB}-${this.sideC}`;
  }
}

// Usage: Polymorphic behavior
const shapes: Shape[] = [
  new Circle(5),
  new Rectangle(10, 20),
  new Triangle(3, 4, 5),
];

console.log('=== Shape Analysis ===\n');
shapes.forEach((shape) => {
  console.log(`${shape.getDescription()}`);
  console.log(`  Area: ${shape.calculateArea().toFixed(2)}`);
  console.log(`  Perimeter: ${shape.calculatePerimeter().toFixed(2)}`);
  console.log();
});

let totalArea = 0;
shapes.forEach((shape) => {
  totalArea += shape.calculateArea();
});
console.log(`Total area: ${totalArea.toFixed(2)}`);
