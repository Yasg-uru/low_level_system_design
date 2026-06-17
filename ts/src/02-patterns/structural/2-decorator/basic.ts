interface Coffee{
  getCost(): number;
  getDescription(): string;
}

class SimpleCoffee implements Coffee {
  getCost(): number {
    return 5;
  }
  getDescription(): string {
    return "Simple coffee";
  }
}

abstract class BaseDecorator implements Coffee{
  constructor(protected coffee: Coffee) {}
  getCost(): number {
    return this.coffee.getCost();
  }
  getDescription(): string {
    return this.coffee.getDescription();
  }
}


class MilkDecorator extends BaseDecorator {
  getCost(): number {
    return this.coffee.getCost() + 2;
  }
  getDescription(): string {
    return this.coffee.getDescription() + ", milk";
  }
}

class SugarDecorator extends BaseDecorator {
  getCost(): number {
    return this.coffee.getCost() + 1;
  }
  getDescription(): string {
    return this.coffee.getDescription() + ", sugar";
  }
}
class WhippedCreamDecorator extends BaseDecorator {
  getCost(): number {
    return this.coffee.getCost() + 3;
  }
  getDescription(): string {
    return this.coffee.getDescription() + ", whipped cream";
  }
}

const simpleCoffee = new SimpleCoffee();
console.log(simpleCoffee.getCost()); // 5
console.log(simpleCoffee.getDescription()); // Simple coffee


const milkCoffee = new MilkDecorator( new SimpleCoffee()); // decorate with milk recursively like this  milkCoffee class is decorated with SimpleCoffee class
console.log(milkCoffee.getCost()); // 7
console.log(milkCoffee.getDescription()); // Simple coffee, milk

const sugarMilkCoffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee())); // decorate with sugar and milk recursively like this sugarMilkCoffee class is decorated with MilkDecorator class which is decorated with SimpleCoffee class
console.log(sugarMilkCoffee.getCost()); // 8
console.log(sugarMilkCoffee.getDescription()); // Simple coffee, milk, sugar

const whippedCreamSugarMilkCoffee = new WhippedCreamDecorator(new SugarDecorator(new MilkDecorator(new SimpleCoffee()))); // decorate with whipped cream, sugar and milk recursively like this whippedCreamSugarMilkCoffee class is decorated with SugarDecorator class which is decorated with MilkDecorator class which is decorated with SimpleCoffee class
console.log(whippedCreamSugarMilkCoffee.getCost()); // 11
console.log(whippedCreamSugarMilkCoffee.getDescription()); // Simple coffee, milk, sugar, whipped cream