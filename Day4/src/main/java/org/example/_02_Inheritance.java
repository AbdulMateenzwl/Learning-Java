package org.example;


class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    void eat() {
        System.out.println(name + " is eating...");
    }

    void sleep() {
        System.out.println(name + " is sleeping...");
    }
}

// Dog class inherits from Animal
class Dog extends Animal {
    String breed;

    Dog(String name, String breed) {
        super(name);
        this.breed = breed;
    }

    void bark() {
        System.out.println(name + " is barking. Breed: " + breed);
    }
}

// Main class to test inheritance
public class _02_Inheritance {
    public static void main(String[] args) {
        Dog dog = new Dog("Buddy", "Golden Retriever");

        // Inherited methods
        dog.eat();
        dog.sleep();

        // Dog-specific method
        dog.bark();
    }
}

