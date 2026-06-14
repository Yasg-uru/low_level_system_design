/**
 * Abstraction using Abstract Classes
 *
 * Hides implementation details and exposes only essential features.
 * Abstract classes define the interface that subclasses must implement.
 */

abstract class Vehicle {
  protected fuelLevel: number = 0;
  protected isRunning: boolean = false;

  abstract start(): void;
  abstract stop(): void;
  abstract accelerate(speed: number): void;

  refuel(amount: number): void {
    this.fuelLevel += amount;
    console.log(`Refueled with ${amount} liters. Current fuel: ${this.fuelLevel}L`);
  }

  getStatus(): string {
    return `Status: ${this.isRunning ? 'Running' : 'Stopped'}, Fuel: ${this.fuelLevel}L`;
  }
}

class Car extends Vehicle {
  private currentSpeed: number = 0;

  start(): void {
    if (this.fuelLevel > 0) {
      this.isRunning = true;
      console.log('🚗 Car engine started');
    } else {
      console.log('Cannot start: No fuel');
    }
  }

  stop(): void {
    this.isRunning = false;
    this.currentSpeed = 0;
    console.log('🚗 Car stopped');
  }

  accelerate(speed: number): void {
    if (this.isRunning && this.fuelLevel > 0) {
      this.currentSpeed = speed;
      this.fuelLevel -= speed * 0.01;
      console.log(`🚗 Car accelerating to ${speed} km/h`);
    }
  }

  getStatus(): string {
    return `${super.getStatus()}, Speed: ${this.currentSpeed} km/h`;
  }
}

class Motorcycle extends Vehicle {
  private currentSpeed: number = 0;

  start(): void {
    if (this.fuelLevel > 0) {
      this.isRunning = true;
      console.log('🏍️  Motorcycle engine started');
    }
  }

  stop(): void {
    this.isRunning = false;
    this.currentSpeed = 0;
    console.log('🏍️  Motorcycle stopped');
  }

  accelerate(speed: number): void {
    if (this.isRunning && this.fuelLevel > 0) {
      this.currentSpeed = speed;
      this.fuelLevel -= speed * 0.005;
      console.log(`🏍️  Motorcycle accelerating to ${speed} km/h`);
    }
  }
}

// Usage
const myCar = new Car();
myCar.refuel(50);
myCar.start();
myCar.accelerate(100);
console.log(myCar.getStatus());
myCar.stop();

const myBike = new Motorcycle();
myBike.refuel(20);
myBike.start();
myBike.accelerate(80);
myBike.stop();
