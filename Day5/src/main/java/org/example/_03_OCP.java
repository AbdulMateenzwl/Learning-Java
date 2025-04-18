package org.example;

abstract class Employee {
    int Id;
    String Name;

    public Employee(int id, String name) {
        Id = id;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    abstract public Float calcuateBonus(Float salary);

    @Override
    public String toString() {
        return "Employee{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                '}';
    }
}

class PermanentEmployee extends Employee {
    public PermanentEmployee(int id, String name) {
        super(id, name);
    }

    @Override
    public Float calcuateBonus(Float salary) {
        return salary * 0.1f;
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(int id, String name) {
        super(id, name);
    }

    @Override
    public Float calcuateBonus(Float salary) {
        return salary * 0.05f;
    }
}

// This is an example of the Open Close Principle (OCP) in Java.
// The Open Close Principle states that software entities (classes, modules, functions, etc.) should be open for extension but closed for modification.
// This means that the behavior of a module can be extended without modifying its source code.
// In this example, we have an abstract class Employee with two concrete implementations: PermanentEmployee and ContractEmployee.
// Each implementation has its own way of calculating the bonus.
// This allows us to add new types of employees in the future without modifying the existing code.
// The main method demonstrates how to use these classes.
public class _03_OCP {
    public static void main(String[] args) {
        Employee emp1 = new PermanentEmployee(4, "Mateen");
        Employee emp2 = new ContractEmployee(5, "Ali");
        System.out.println(emp1.calcuateBonus(120000f));
        System.out.println(emp2.calcuateBonus(120000f));

    }
}
