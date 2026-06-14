/**
 * ANTI-PATTERN: Liskov Substitution Principle Violation
 *
 * A square inheriting from rectangle breaks the contract.
 * Cannot safely substitute child for parent.
 */

class Rectangle {
  protected width: number;
  protected height: number;

  constructor(width: number, height: number) {
    this.width = width;
    this.height = height;
  }

  setWidth(width: number): void {
    this.width = width;
  }

  setHeight(height: number): void {
    this.height = height;
  }

  getArea(): number {
    return this.width * this.height;
  }
}

class Square extends Rectangle {
  constructor(size: number) {
    super(size, size);
  }

  setWidth(width: number): void {
    this.width = width;
    this.height = width; // Enforce square constraint
  }

  setHeight(height: number): void {
    this.height = height;
    this.width = height; // Enforce square constraint
  }
}

// PROBLEM: Violates Liskov Substitution Principle
function testRectangle(rect: Rectangle) {
  rect.setWidth(5);
  rect.setHeight(10);
  const area = rect.getArea();

  // Expected: 5 * 10 = 50
  console.log(`Area: ${area}`); // But if rect is Square, area will be 100!

  if (area !== 50) {
    console.log('ERROR: Unexpected area! Cannot substitute Square for Rectangle!');
  }
}

const rectangle = new Rectangle(0, 0);
const square = new Square(0);

testRectangle(rectangle); // Works fine
testRectangle(square);    // BREAKS! Violates contract
