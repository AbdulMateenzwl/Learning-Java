package com.student.models;

import java.util.List;

public interface IStudent {
    String getName();
    void setName(String name);
    String getEmail();
    void setEmail(String email);
    List<ICourse> getEnrolledCourses();
    void setEnrolledCourses(List<ICourse> enrolledCourses);
    void addCourse(ICourse course);
    void removeCourse(ICourse course);
    void displayEnrolledCourses();
}