package com.student.models;

import com.student.models.Course;
import java.util.ArrayList;
import java.util.List;

public class Student {

    private String name;
    private String email;
    private List<Course> enrolledCourses;

    // Constructor
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
        this.enrolledCourses = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    // Method to enroll student in a course
    public void addCourse(Course course) {
        if (course != null && !enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    // Method to drop a course (optional)
    public void removeCourse(Course course) {
        enrolledCourses.remove(course);
    }

    // Method to display enrolled courses (can be used for printing)
    public void displayEnrolledCourses() {
        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses enrolled.");
        } else {
            System.out.println(name + "'s Enrolled Courses:");
            for (Course course : enrolledCourses) {
                System.out.println(course);
            }
        }
    }

    // Overriding toString() to display student details
    @Override
    public String toString() {
        return "Student{name='" + name + "', email='" + email + "', enrolledCourses=" + enrolledCourses + "}";
    }
}
