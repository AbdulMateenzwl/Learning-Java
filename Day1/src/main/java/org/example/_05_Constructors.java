package org.example;

class ParentClass {
    // fields
    public int field1;
    public String field2;

    // This is the default constructor
    ParentClass() {
        System.out.println("Default Constructor");
        field1 = 0;
        field2 = "Default";
    }

    // we can have multiple constructors
    ParentClass(int val) {
        this.field1 = val;
    }

    // we can overload constructors
    ParentClass(String str) {
        this.field2 = str;
    }

    // Constructor with two parameters
    ParentClass(int val, String str) {
        this.field1 = val;
        this.field2 = str;
    }
}


class ChildClass extends ParentClass {
    public double field;

    // Constructor of child class
    ChildClass() {
        // Calling the constructor of parent class
        super();
        System.out.println("Default Child class constructor");
        this.field = 0.0;
    }

    // Constructor of child class
    ChildClass(double val) {
        // Calling the constructor of parent class
        super(5);
        System.out.println("Child class constructor with single parameter");
        this.field = val;
    }

    ChildClass(int val, String str, double val2) {
        // Calling the constructor of parent class
        super(val, str);
        System.out.println("Child class constructor with two parameters");
        this.field = val2;
    }
}

class Constructors {
    // Main method
    public static void main(String[] args) {
        ParentClass parent = new ParentClass(5, "Hello");


        System.out.println("Parent class field1: " + parent.field1);
        System.out.println("Parent class field2: " + parent.field2);

        ChildClass child = new ChildClass(10, "name", 5.5);

        System.out.print("Child class field1: " + child.field1 +
                "\nChild class field2: " + child.field2 +
                "\nChild class field3: " + child.field);

    }
}