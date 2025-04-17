package com.student;

import com.student.services.StudentService;
import com.student.utils.InputHelper;

public class Main {

    public static void main(String[] args) {
        // Sample Data
        initializeSampleData();

        // Main Menu for user interaction
        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Create Course");
            System.out.println("3. Enroll Student in Course");
            System.out.println("4. View All Students");
            System.out.println("5. View All Courses");
            System.out.println("6. Exit");

            int choice = InputHelper.readInt("Choose an option:");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    createCourse();
                    break;
                case 3:
                    enrollStudentInCourse();
                    break;
                case 4:
                    StudentService.viewAllStudents();
                    break;
                case 5:
                    StudentService.viewAllCourses();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Initialize some sample students and courses for testing
    private static void initializeSampleData() {
        StudentService.createCourse("Java Programming", 3);
        StudentService.createCourse("Data Structures", 4);
        StudentService.createCourse("Database Systems", 3);

        StudentService.addStudent("Mateen", "mateen@mail.com");
        StudentService.addStudent("Test", "test@mail.com");
    }

    // Method to add a student interactively
    private static void addStudent() {
        String name = InputHelper.readString("Enter student's name:");
        String email = InputHelper.readString("Enter student's email:");
        StudentService.addStudent(name, email);
    }

    // Method to create a course interactively
    private static void createCourse() {
        String courseName = InputHelper.readString("Enter course name:");
        int creditHours = InputHelper.readInt("Enter credit hours for the course:");
        StudentService.createCourse(courseName, creditHours);
    }

    // Method to enroll a student in a course interactively
    private static void enrollStudentInCourse() {
        StudentService.enrollStudentInteractively();
    }
}
