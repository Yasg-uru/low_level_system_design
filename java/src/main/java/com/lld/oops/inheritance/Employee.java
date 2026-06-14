package com.lld.oops.inheritance;

/**
 * Abstract base class for employee hierarchy.
 * Shows inheritance in Java.
 */
public abstract class Employee {
    protected String id;
    protected String name;
    protected double salary;

    public Employee(String id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public abstract double calculateBonus();
    public abstract String getRole();

    public String getDetails() {
        return name + " (" + getRole() + ") - ID: " + id;
    }

    public double getSalary() {
        return salary;
    }

    public void giveRaise(double percentage) {
        salary += salary * (percentage / 100.0);
        System.out.println(name + " got a " + percentage + "% raise. New salary: $" + salary);
    }
}
