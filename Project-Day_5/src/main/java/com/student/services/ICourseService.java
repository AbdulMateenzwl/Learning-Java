package com.student.services;

import com.student.models.ICourse;

public interface ICourseService {
    void addCourse(String courseName, int creditHours);
    void viewAllCourses();
    ICourse chooseCourse();
}