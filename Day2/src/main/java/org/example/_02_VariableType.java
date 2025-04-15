package org.example;


// A simple class to demonstrate Instance and Static (Class) Variables
class Counter {
    // ✅ Instance Variable – unique to each object
    int instanceCount = 0;

    // ✅ Static/Class Variable – shared among all objects
    static int staticCount = 0;

    // Constructor
    Counter() {
        instanceCount++;  // Increases only this object's count
        staticCount++;    // Increases global count for all objects
    }

    void displayCounts() {
        System.out.println("Instance Count: " + instanceCount);
        System.out.println("Static Count: " + staticCount);
    }
}

class _02_VariableType {
    public static void main(String[] args) {
        System.out.println("Creating object c1:");
        Counter c1 = new Counter();
        c1.displayCounts();

        System.out.println("Creating object c2:");
        Counter c2 = new Counter();
        c2.displayCounts();

        System.out.println("Creating object c3:");
        Counter c3 = new Counter();
        c3.displayCounts();

        System.out.println("Accessing static variable without object:");
        System.out.println("Counter.staticCount = " + Counter.staticCount);
    }
}
