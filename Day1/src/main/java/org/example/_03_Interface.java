package org.example;

interface Interface {
    // All interface methods are implicitly public and abstract
    void method1();

    // All interface fields are implicitly public, static, and final
    int CONSTANT = 10;

    // Default method with a body
    default void defaultMethod() {

        System.out.println("Default Method, Prints Interface field: " + CONSTANT);
    }

    // Static method with a body
    static void staticMethod() {
        System.out.println("This is a static method.");
    }
}

class InterfaceImpl implements Interface{
    // Implementing the abstract method
    @Override
    public void method1() {
        System.out.println("Method1 implementation.");
    }
}


class Inteface {
    public static void main(String[] args) {
        InterfaceImpl obj = new InterfaceImpl();
        obj.method1();
        obj.defaultMethod();
        Interface.staticMethod();
    }
}
