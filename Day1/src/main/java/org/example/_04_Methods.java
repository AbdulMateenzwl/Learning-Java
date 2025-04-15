package org.example;

class Methods {
    // Method Overloading
    public void display(int a) {
        System.out.println("Integer: " + a);
    }

    public void display(String b) {
        System.out.println("String: " + b);
    }

    // Method Overriding
    public void show() {
        System.out.println("Base class show method");
    }
}

class Derived extends Methods {
    @Override
    public void show() {
        System.out.println("Derived class show method");
    }
}

class MethodsMain {
    public static void main(String[] args) {
        Methods obj = new Methods();
        obj.display(5);
        obj.display("Hello");

        Derived derivedObj = new Derived();
        derivedObj.show(); // Calls the overridden method in Derived class
    }
}