package org.example;

class StaticPolymorphism {
    // Method Overloading (Static Polymorphism)
    void show() {
        System.out.println("No arguments");
    }

    void show(String name) {
        System.out.println("Hello, " + name);
    }

    void show(int a, int b) {
        System.out.println("Sum: " + (a + b));
    }
}

class DynamicPolymorphismBase {
    void greet() {
        System.out.println("Hello from Base class");
    }
}

class DynamicPolymorphismDerived extends DynamicPolymorphismBase {
    @Override
    void greet() {
        System.out.println("Hello from Derived class");
    }
}

public class _01_Polymorphism {
    public static void main(String[] args) {
        // Static Polymorphism (Compile-time)
        StaticPolymorphism staticObj = new StaticPolymorphism();
        staticObj.show();
        staticObj.show("Alice");
        staticObj.show(10, 20);

        // Dynamic Polymorphism (Run-time)
        DynamicPolymorphismBase dynObj = new DynamicPolymorphismDerived();
        dynObj.greet();  // Method in derived class is called
    }
}
