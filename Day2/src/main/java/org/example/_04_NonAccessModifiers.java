package org.example;

// ✅ abstract modifier — used to define abstract classes and methods
abstract class Animal {
    // abstract variable (no body)
    // variable can be declared but not initialized
    int val;

    // abstract method (no body)
    abstract void makeSound();

    // regular method
    void eat() {
        System.out.println("This animal eats food.");
    }
}

// Concrete class that inherits abstract class
class Dog extends Animal {

    // Constructor
    Dog() {
        // Initialize the abstract variable
        val = 10; // Must be initialized in the constructor or method
    }

    // Must implement abstract method from Animal
    void makeSound() {
        System.out.println("Dog barks");
        if(val == 0) {
            System.out.println("Value is not initialized");
        } else {
            System.out.println("Value is: " + val);
        }
    }
}

class Demo {
    // ✅ static variable – shared across all instances of Demo class
    static int staticCount = 0;

    // ✅ final variable – constant (value cannot be changed once assigned)
    final int finalNumber;

    // Constructor
    Demo(int val) {
        finalNumber = val; // final variable must be assigned exactly once
        staticCount++;
    }

    // ✅ static method – belongs to class, not object
    static void showStaticInfo() {
        System.out.println("Static method called. Count: " + staticCount);
    }

    // regular method to show final variable
    void showFinalValue() {
        System.out.println("Final number: " + finalNumber);
    }

    // ✅ final method – cannot be overridden
    final void showFinalMethod() {
        System.out.println("This method is final and cannot be overridden.");
    }

}

// Trying to override a final method (Uncomment to see error)
//class SubDemo extends Demo {
//    @Override
//    void showFinalMethod() {
//        System.out.println("Trying to override final method"); // This will cause a compile-time error
//    }
//}

final class FinalClass {
    // This class cannot be extended
}

// Trying to extend a final class (Uncomment to see error)
//class ExtendedClass extends FinalClass {
//    // This will cause a compile-time error
//}

public class _04_NonAccessModifiers {
    public static void main(String[] args) {
        // Using abstract class
        Animal myDog = new Dog();
        myDog.makeSound(); // Output: Dog barks
        myDog.eat();       // Output: This animal eats food.

        // Using final and static variables/methods
        Demo obj1 = new Demo(100);
        obj1.showFinalValue();
        Demo.showStaticInfo(); // Call static method via class

        Demo obj2 = new Demo(200);
        obj2.showFinalValue();
        Demo.showStaticInfo(); // Count increases due to static nature
    }
}
