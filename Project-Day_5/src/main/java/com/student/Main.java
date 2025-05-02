package com.student;

import com.student.models.ICourse;
import com.student.models.IStudent;
import com.student.services.CourseService;
import com.student.services.ServiceFactory;
import com.student.services.StudentService;
import com.student.utils.InputHelper;

public class Main {


    private static final StudentService studentService = ServiceFactory.getStudentService();
    private static final CourseService courseService = ServiceFactory.getCourseService();
    private static final InputHelper inputHelper = new InputHelper();

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

            int choice = inputHelper.readInt("Choose an option:");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> createCourse();
                case 3 -> enrollStudentInCourse();
                case 4 -> studentService.viewAllStudents();
                case 5 -> courseService.viewAllCourses();
                case 6 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Initialize some sample students and courses for testing
    private static void initializeSampleData() {
        courseService.addCourse("Java Programming", 3);
        courseService.addCourse("Data Structures", 4);
        courseService.addCourse("Database Systems", 3);

        studentService.addStudent("Mateen", "mateen@mail.com");
        studentService.addStudent("Test", "test@mail.com");
    }

    // Method to add a student interactively
    private static void addStudent() {
        String name = inputHelper.readString("Enter student's name:");
        String email = inputHelper.readString("Enter student's email:");
        studentService.addStudent(name, email);
    }

    // Method to create a course interactively
    private static void createCourse() {
        String courseName = inputHelper.readString("Enter course name:");
        int creditHours = inputHelper.readInt("Enter credit hours for the course:");
        courseService.addCourse(courseName, creditHours);
    }

    // Method to enroll a student in a course interactively
    private static void enrollStudentInCourse() {
        IStudent stu = studentService.chooseStudent();
        ICourse course = courseService.chooseCourse();
        studentService.addCourseToStudent(stu, course);
    }
}
