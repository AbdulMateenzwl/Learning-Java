package org.example;

// Custom Exception Example
class AgeNotValidException extends Exception {
    AgeNotValidException(String message) {
        super(message);
    }
}

public class _06_ExceptionHandlingExamples {

    // A method that throws a checked exception
    static void validateAge(int age) throws AgeNotValidException {
        if (age < 18) {
            throw new AgeNotValidException("Age is not valid for voting!");
        } else {
            System.out.println("You are eligible to vote.");
        }
    }

    public static void main(String[] args) {
        // Basic try-catch block
        try {
            int result = 10 / 0; // This will throw ArithmeticException
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        }

        // Multiple catch blocks
        try {
            int[] numbers = new int[5];
            numbers[10] = 50; // ArrayIndexOutOfBoundsException
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic Error");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array Index Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Generic Error: " + e.getMessage());
        }

        // Finally block – always runs
        try {
            System.out.println("Trying to open a file...");
            // Simulate file logic
        } catch (Exception e) {
            System.out.println("Exception caught while working with file.");
        } finally {
            System.out.println("This will always run (e.g., close file)");
        }

        // Using 'throw' and 'throws' with custom exceptions
        try {
            validateAge(16); // This will throw custom exception
        } catch (AgeNotValidException e) {
            System.out.println("Custom Exception Caught: " + e.getMessage());
        }

        // Nested try-catch
        try {
            try {
                int num = Integer.parseInt("abc"); // NumberFormatException
            } catch (NumberFormatException e) {
                System.out.println("Nested Catch: Invalid number format");
            }

            String str = null;
            System.out.println(str.length()); // NullPointerException

        } catch (NullPointerException e) {
            System.out.println("Outer Catch: Null value detected");
        }

        System.out.println("Program continues after exception handling...");
    }
}
