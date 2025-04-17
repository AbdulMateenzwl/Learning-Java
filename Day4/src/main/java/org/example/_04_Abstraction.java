package org.example;

// AbstractionExample.java

abstract class AnimalClass {
    // Abstract method (does not have a body)
    abstract void makeSound();

    // Concrete method
    void sleep() {
        System.out.println("Sleeping...");
    }
}

// Dog class inherits Animal and provides implementation of abstract method
class Lion extends AnimalClass {
    @Override
    void makeSound() {
        System.out.println("Lion Roars");
    }
}

// Cat class inherits Animal and provides implementation of abstract method
class Cat extends AnimalClass {
    @Override
    void makeSound() {
        System.out.println("Cat meows ");
    }
}

public class _04_Abstraction{
    public static void main(String[] args) {
        AnimalClass myLion = new Lion();
        AnimalClass myCat = new Cat();

        myLion.makeSound(); // Output: Lion Roars
        myLion.sleep();     // Output: Sleeping...

        myCat.makeSound(); // Output: Cat meows
        myCat.sleep();     // Output: Sleeping...
    }
}
