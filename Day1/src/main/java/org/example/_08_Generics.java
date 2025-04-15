package org.example;

import java.util.*;

// Generic class
class Box<T> {
    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}

// Generic method
class Util {
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }

    // Bounded type parameter
    public static <T extends Number> double sum(T a, T b) {
        return a.doubleValue() + b.doubleValue();
    }

    // Wildcard capture and helper method
    public static void addBox(Box<?> box, Object value) {
        addHelper(box, value);
    }

    private static <T> void addHelper(Box<T> box, Object value) {
        try {
            box.set((T) value);
        } catch (ClassCastException e) {
            System.out.println("Type mismatch: " + e.getMessage());
        }
    }
}

class GenericsDemo {

    public static void main(String[] args) {

        // Generic Types
        Box<Integer> intBox = new Box<>();
        intBox.set(123);
        System.out.println("Generic Box: " + intBox.get());

        // Raw Types (not recommended)
        Box rawBox = new Box(); // raw type
        rawBox.set("Raw String"); // compiles but unsafe
        System.out.println("Raw Box: " + rawBox.get());

        // Generic Methods
        String[] names = {"Ali", "Ahmed", "Sara"};
        Util.printArray(names);

        // Bounded Type Parameters
        System.out.println("Sum: " + Util.sum(10, 20));

        // Generic Methods and Bounded Type Parameters
        Double result = Util.sum(12, 13.3);
        System.out.println("Double Sum: " + result);

        // Generics, Inheritance, and Subtypes
        List<Integer> intList = new ArrayList<>();
        intList.add(1);
//         List<Object> objList = intList; // ❌ not allowed
        List<? extends Number> numList = intList; // ✅ allowed

        // Type Inference
        Box<String> strBox = new Box<>();
        strBox.set("Hello");
        System.out.println("Inferred Box: " + strBox.get());

        // Wildcards
        printList(Arrays.asList(1, 2, 3));
        printList(Arrays.asList("A", "B", "C"));

        // Upper Bounded Wildcards
        List<? extends Number> numbers = Arrays.asList(1, 2, 3);
        printUpperBounded(numbers);

        // Unbounded Wildcards
        List<String> strings = Arrays.asList("X", "Y", "Z");
        printUnbounded(strings);

        // Lower Bounded Wildcards
        List<? super Integer> superList = new ArrayList<>();
        superList.add(10);
        printLowerBounded(superList);

        // Wildcards and Subtyping
        List<Object> objs = new ArrayList<>();
        objs.add("Hello");
        wildcardSubtype(objs);

        // Wildcard Capture and Helper Methods
        Box<Double> doubleBox = new Box<>();
        Util.addBox(doubleBox, 15.5);
        System.out.println("Captured Box: " + doubleBox.get());

        // Guidelines for Wildcard Use
        // Producer Extends, Consumer Super (PECS principle)

        // Type Erasure
        System.out.println("Type Erasure demo: " + strBox.getClass().getName());

        // Erasure of Generic Types
        if (intBox.getClass() == strBox.getClass()) {
            System.out.println("Both boxes are the same class after erasure.");
        }

        // Non-Reifiable Types (e.g., List<String>[] is not allowed)
        // List<String>[] listArray = new List<String>[10]; // ❌ compile error

        // Restrictions on Generics
        // Can't create generic arrays or instantiate with primitives
        // Box<int> intBox2 = new Box<>(); // ❌ not allowed
    }

    // Helper Methods

    // Wildcards
    public static void printList(List<?> list) {
        for (Object obj : list) {
            System.out.println("Wildcard: " + obj);
        }
    }

    // Upper Bounded Wildcards
    public static void printUpperBounded(List<? extends Number> list) {
        for (Number num : list) {
            System.out.println("Upper Bounded: " + num);
        }
    }

    // Unbounded Wildcards
    public static void printUnbounded(List<?> list) {
        for (Object obj : list) {
            System.out.println("Unbounded: " + obj);
        }
    }

    // Lower Bounded Wildcards
    public static void printLowerBounded(List<? super Integer> list) {
        for (Object obj : list) {
            System.out.println("Lower Bounded: " + obj);
        }
    }

    // Wildcards and Subtyping
    public static void wildcardSubtype(List<?> list) {
        for (Object obj : list) {
            System.out.println("Subtype wildcard: " + obj);
        }
    }
}
