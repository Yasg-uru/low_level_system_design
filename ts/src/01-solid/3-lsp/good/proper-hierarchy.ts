/**
 * GOOD: Following Liskov Substitution Principle
 *
 * Subclasses can safely be used wherever parent type is expected.
 * Maintains contract and expectations.
 */

interface IShape {
  getArea(): number;
  getPerimeter(): number;
}

class Rectangle implements IShape {
  constructor(protected width: number, protected height: number) {}

  getArea(): number {
    return this.width * this.height;
  }

  getPerimeter(): number {
    return 2 * (this.width + this.height);
  }
}

class Square implements IShape {
  constructor(private side: number) {}

  getArea(): number {
    return this.side * this.side;
  }

  getPerimeter(): number {
    return 4 * this.side;
  }
}

// Client code works with both implementations safely
function calculateTotalArea(shapes: IShape[]): number {
  return shapes.reduce((total, shape) => total + shape.getArea(), 0);
}

// Usage: Both can be used interchangeably
const shapes: IShape[] = [
  new Rectangle(5, 10),
  new Square(5),
  new Rectangle(3, 7),
];

console.log(`Total area: ${calculateTotalArea(shapes)}`); // Works as expected
