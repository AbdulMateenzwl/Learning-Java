package org.example;

// library of object class
// import java.lang.Object;

class Person implements Cloneable {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person clone() {
        return new Person(this.name, this.age);
    }

    public Boolean equals(Person person) {
        return (this.name.equals(person.name) && this.age == person.age);
    }
}

// Every class has Object class as superclass
class ObjectClass { // explicit inheriting from Object class
    public static void main(String[] args) {

        Person person1 = new Person("person1", 24);
        Person person2 = new Person("person2", 19);

        // clone is a method to create hard copy an object
        Person person3 = person1.clone();

        // equal methods check if the two objects are equal or not
        // my default it compares the memory address of two Objects if not implemented by child class
        Boolean equals = person1.equals(person3);

        // Gets the class of the object intiated
        Class personClass = person1.getClass();

        // Gets the hashCode value of the object
        int hashCode = person2.hashCode();

        // Generates the string representation of the Object
        // if not implemented it returns the Memory Address of the Object
        String personString = person3.toString();

        System.out.println(hashCode);

    }
}
