package com.student.models;

import java.time.LocalDate;

public class Course implements ICourse {

    private static int courseCounter = 0;

    private final int courseId;
    private String courseName;
    private int creditHours;
    private LocalDate createdOn;

    public Course(String courseName, int creditHours) {
        this.courseId = ++courseCounter;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.createdOn = LocalDate.now();
    }

    @Override
    public int getCourseId() {
        return courseId;
    }

    @Override
    public String getCourseName() {
        return courseName;
    }

    @Override
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public int getCreditHours() {
        return creditHours;
    }

    @Override
    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    @Override
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    @Override
    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Course{Course ID: " + courseId +
                ", Course Name: " + courseName +
                ", Credit Hours: " + creditHours +
                ", Created On: " + createdOn +"}";
    }
}
