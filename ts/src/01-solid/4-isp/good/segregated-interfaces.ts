/**
 * GOOD: Segregated Interfaces (Following ISP)
 *
 * Clients depend only on methods they actually use.
 */

interface IWorkable {
  work(): void;
}

interface IEatable {
  eat(): void;
}

interface ISleepable {
  sleep(): void;
}

class HumanWorker implements IWorkable, IEatable, ISleepable {
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

class RobotWorker implements IWorkable {
  work(): void {
    console.log('Robot is working');
  }
}

// Now RobotWorker only implements what it needs
// Clients can depend on IWorkable without knowing about eating/sleeping
function manageWorkers(workers: IWorkable[]): void {
  workers.forEach((worker) => worker.work());
}

const workers: IWorkable[] = [new HumanWorker(), new RobotWorker()];
manageWorkers(workers);
