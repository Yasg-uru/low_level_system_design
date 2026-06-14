package com.lld.oops.inheritance;

/**
 * Concrete implementation: Manager
 * Inherits from Employee, adds team management.
 */
public class Manager extends Employee {
    private int teamSize;

    public Manager(String id, String name, double salary, int teamSize) {
        super(id, name, salary);
        this.teamSize = teamSize;
    }

    @Override
    public String getRole() {
        return "Manager";
    }

    @Override
    public double calculateBonus() {
        return salary * 0.15 + teamSize * 500;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + " - Team size: " + teamSize;
    }
}
