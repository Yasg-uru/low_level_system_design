/**
 * ANTI-PATTERN: Fat Interface (Violates ISP)
 *
 * Clients forced to depend on methods they don't use.
 */

interface IWorker {
  work(): void;
  eat(): void;
  sleep(): void;
}

class HumanWorker implements IWorker {
  work(): void {
    console.log('Human is working');
  }

  eat(): void {
    console.log('Human is eating');
  }

  sleep(): void {
    console.log('Human is sleeping');
  }
}

class RobotWorker implements IWorker {
  work(): void {
    console.log('Robot is working');
  }

  eat(): void {
    throw new Error('Robot cannot eat!');
  }

  sleep(): void {
    throw new Error('Robot cannot sleep!');
  }
}

// PROBLEM: RobotWorker must implement eat() and sleep() even though it doesn't need them
