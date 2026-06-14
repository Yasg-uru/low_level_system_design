/**
 * Inheritance: Creating class hierarchies
 *
 * Allows classes to inherit properties and methods from parent classes,
 * promoting code reuse and establishing relationships.
 */

abstract class Employee {
  protected id: string;
  protected name: string;
  protected salary: number;

  constructor(id: string, name: string, salary: number) {
    this.id = id;
    this.name = name;
    this.salary = salary;
  }

  abstract calculateBonus(): number;

  abstract getRole(): string;

  getDetails(): string {
    return `${this.name} (${this.getRole()}) - ID: ${this.id}`;
  }

  getSalary(): number {
    return this.salary;
  }

  giveRaise(percentage: number): void {
    this.salary += this.salary * (percentage / 100);
    console.log(`${this.name} got a ${percentage}% raise. New salary: $${this.salary}`);
  }
}

class Manager extends Employee {
  private teamSize: number;

  constructor(id: string, name: string, salary: number, teamSize: number) {
    super(id, name, salary);
    this.teamSize = teamSize;
  }

  getRole(): string {
    return 'Manager';
  }

  calculateBonus(): number {
    return this.salary * 0.15 + this.teamSize * 500;
  }

  getDetails(): string {
    return `${super.getDetails()} - Team size: ${this.teamSize}`;
  }
}

class Developer extends Employee {
  private experience: number;

  constructor(id: string, name: string, salary: number, experience: number) {
    super(id, name, salary);
    this.experience = experience;
  }

  getRole(): string {
    return 'Developer';
  }

  calculateBonus(): number {
    return this.salary * 0.10 + this.experience * 1000;
  }

  getDetails(): string {
    return `${super.getDetails()} - Experience: ${this.experience} years`;
  }
}

class Intern extends Employee {
  getRole(): string {
    return 'Intern';
  }

  calculateBonus(): number {
    return 0;
  }
}

// Usage
const employees: Employee[] = [
  new Manager('M001', 'Alice', 100000, 5),
  new Developer('D001', 'Bob', 80000, 7),
  new Intern('I001', 'Charlie', 20000),
];

console.log('=== Employee Details ===');
employees.forEach((emp) => {
  console.log(emp.getDetails());
  console.log(`  Salary: $${emp.getSalary()}`);
  console.log(`  Bonus: $${emp.calculateBonus()}`);
});

console.log('\n=== After 10% Raise ===');
employees[0].giveRaise(10);
employees[1].giveRaise(10);
