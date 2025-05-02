package com.student.models;

import java.util.ArrayList;
import java.util.List;

public class Student implements IStudent {

    private String name;
    private String email;
    private List<ICourse> enrolledCourses;

    // Constructor
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
        this.enrolledCourses = new ArrayList<>();
    }

    // Getters and Setters
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public List<ICourse> getEnrolledCourses() {
        return enrolledCourses;
    }

    @Override
    public void setEnrolledCourses(List<ICourse> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public void addCourse(ICourse course) {
        if (course != null && !enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    @Override
    public void removeCourse(ICourse course) {
        enrolledCourses.remove(course);
    }

    @Override
    public void displayEnrolledCourses() {
        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses enrolled.");
        } else {
            System.out.println(name + "'s Enrolled Courses:");
            for (ICourse course : enrolledCourses) {
                System.out.println(course);
            }
        }
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', email='" + email + "', enrolledCourses=" + enrolledCourses + "}";
    }
}
