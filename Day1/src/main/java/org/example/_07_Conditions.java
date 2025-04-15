package org.example;

class Conditions {
    public static void main(String[] args) {
        int number = 5;

        // 1. Simple if
        if (number > 0) {
            System.out.println("The number is positive.");
        }

        // 2. if-else
        if (number % 2 == 0) {
            System.out.println("Even number.");
        } else {
            System.out.println("Odd number.");
        }

        // 3. if-else if-else
        int marks = 85;
        if (marks >= 90) {
            System.out.println("Grade: A+");
        } else if (marks >= 80) {
            System.out.println("Grade: A");
        } else if (marks >= 70) {
            System.out.println("Grade: B");
        } else {
            System.out.println("Grade: C or below");
        }

        // 4. Traditional switch-case
        int day = 3;
        switch (day) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            default:
                System.out.println("Another day");
        }

        // 5. Enhanced switch (Java 14+)
        String fruit = "Apple";
        switch (fruit) {
            case "Apple" -> System.out.println("You chose Apple.");
            case "Banana" -> System.out.println("You chose Banana.");
            case "Mango" -> System.out.println("You chose Mango.");
            default -> System.out.println("Unknown fruit.");
        }

        // 6. Switch with yield (Java 14+)
        String grade = "A";
        String message = switch (grade) {
            case "A" -> "Excellent!";
            case "B" -> "Very good!";
            case "C" -> "Good!";
            case "D", "E" -> "Needs Improvement!";
            default -> {
                String msg = "Invalid grade!";
                yield msg;
            }
        };
        System.out.println("Grade Message: " + message);
    }
}
