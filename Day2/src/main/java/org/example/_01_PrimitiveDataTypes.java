package org.example;

public class _01_PrimitiveDataTypes {
    public static void main(String[] args) {

        // byte, short, int, long
        byte smallNumber = (byte) 100; // 1 byte
        smallNumber = (byte) 128; // will be cast to -128 due to overflow
        // byte range is -128 to 127

        // short range is -32,768 to 32,767
        short mediumNumber = 10000; // 2 bytes

        // int range is -2,147,483,648 to 2,147,483,647
        int largeNumber = 1000000; // 4 bytes

        // long range is -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
        long veryLargeNumber = 10000000000L; // 8 bytes

        // float, double
        // float range is approximately -3.40282347E+38 to 3.40282347E+38
        float decimalNumber = 10.5f; // 4 bytes
        // double range is approximately -1.79769313486231570E+308 to 1.79769313486231570E+308
        double largeDecimalNumber = 10.5; // 8 bytes

        // boolean can be true or false
        boolean isActive = true;

        // character
        // char range is 0 to 65,535 (or -2^15 to 2^15-1)
        char letter = 'A';

        // Printing the values
        System.out.println("Byte: " + smallNumber);
        System.out.println("Short: " + mediumNumber);
        System.out.println("Int: " + largeNumber);
        System.out.println("Long: " + veryLargeNumber);
        System.out.println("Float: " + decimalNumber);
        System.out.println("Double: " + largeDecimalNumber);
        System.out.println("Boolean: " + isActive);
        System.out.println("Char: " + letter);

        // ------- Reference Data Types -------

        class Person {
            String name;
            int age;

            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }

            void greet() {
                System.out.println("Hi, my name is " + name + " and I am " + age + " years old.");
            }
        }

        // ✅ String - A built-in reference type
        String greeting = "Hello, World!";
        System.out.println("Greeting: " + greeting.toUpperCase()); // Reference method call

        // ✅ Arrays - Reference types
        int[] numbers = {1, 2, 3, 4, 5};
        System.out.println("First number in array: " + numbers[0]);

        // ✅ Custom Object - Reference type
        Person p1 = new Person("Alice", 25);
        Person p2 = p1;  // Both p1 and p2 point to the same object

        System.out.println("Before change:");
        p1.greet();
        p2.greet();

        // Modify through one reference
        p2.name = "Bob";

        System.out.println("After changing name using p2:");
        p1.greet();  // Reflects in p1 as well
        p2.greet();

        // ✅ Null reference
        Person p3 = null;
        if (p3 == null) {
            System.out.println("p3 is null and doesn't refer to any object.");
        }

        // ✅ Wrapper classes (also reference types)
        Integer intObj = 100;
        Double doubleObj = 55.5;
        Character charObj = 'A';

        System.out.println("Wrapper values: " + intObj + ", " + doubleObj + ", " + charObj);

        // ✅ Comparing reference types
        String s1 = new String("hello");
        String s2 = new String("hello");

        System.out.println("s1 == s2: " + (s1 == s2));       // false: different objects
        System.out.println("s1.equals(s2): " + s1.equals(s2)); // true: same content

    }
}