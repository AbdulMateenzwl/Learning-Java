package com.student.services;

import java.util.List;

import com.student.models.ICourse;

public abstract class BaseCourseService {

    public abstract void addCourse(String courseName, int creditHours);

    public abstract List<ICourse> getAllCourses();

    public abstract ICourse chooseCourse();
}