package com.lld.oops.inheritance;

/**
 * Concrete implementation: Intern
 * Inherits from Employee, no bonus.
 */
public class Intern extends Employee {

    public Intern(String id, String name, double salary) {
        super(id, name, salary);
    }

    @Override
    public String getRole() {
        return "Intern";
    }

    @Override
    public double calculateBonus() {
        return 0;
    }
}
