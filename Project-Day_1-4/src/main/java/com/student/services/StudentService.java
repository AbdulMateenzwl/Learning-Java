package com.student.services;

import com.student.models.Course;
import com.student.models.Student;
import com.student.utils.InputHelper;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private static final List<Student> students = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();

    // Add a new student
    public static void addStudent(String name, String email) {
        Student student = new Student(name, email);
        students.add(student);
        System.out.println("Student added successfully: " + name);
    }

    // Enroll student in a course
    public static void enrollStudentInCourse(Student student, Course course) {
        if (student == null || course == null) {
            System.out.println("Invalid student or course.");
            return;
        }
        student.addCourse(course);
        System.out.println(student.getName() + " has been enrolled in " + course.getCourseName());
    }

    // View all students
    public static void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // View all courses
    public static void viewAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            for (Course course : courses) {
                System.out.println(course);
            }
        }
    }

    // Create new course
    public static void createCourse(String courseName, int creditHours) {
        Course course = new Course(courseName, creditHours);
        courses.add(course);
        System.out.println("Course created: " + courseName);
    }

    // Choose a course from a list
    public static Course chooseCourse() {
        return InputHelper.chooseFromList(courses, "Choose a course to enroll:");
    }

    // Choose a student from a list
    public static Student chooseStudent() {
        return InputHelper.chooseFromList(students, "Choose a student:");
    }

    // Enroll student interactively
    public static void enrollStudentInteractively() {
        Student student = chooseStudent();
        Course course = chooseCourse();
        if (student != null && course != null) {
            enrollStudentInCourse(student, course);
        }
    }
}
