package com.student.models;

import java.time.LocalDate;

public class Course {

    // Static counter to assign course IDs
    private static int courseCounter = 0;

    // Instance variables
    private final int courseId;
    private String courseName;
    private int creditHours;
    private LocalDate createdOn;

    // Constructor
    public Course(String courseName, int creditHours) {
        this.courseId = ++courseCounter;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.createdOn = LocalDate.now();
    }

    // Getters and Setters (Encapsulation)
    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    // Method to display course info
    @Override
    public String toString() {
        return "Course{Course ID: " + courseId +
                ", Course Name: " + courseName +
                ", Credit Hours: " + creditHours +
                ", Created On: " + createdOn+"}";
    }
}
