package com.lld.oops.inheritance;

/**
 * Concrete implementation: Developer
 * Inherits from Employee, adds experience.
 */
public class Developer extends Employee {
    private int experience;

    public Developer(String id, String name, double salary, int experience) {
        super(id, name, salary);
        this.experience = experience;
    }

    @Override
    public String getRole() {
        return "Developer";
    }

    @Override
    public double calculateBonus() {
        return salary * 0.10 + experience * 1000;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + " - Experience: " + experience + " years";
    }
}
