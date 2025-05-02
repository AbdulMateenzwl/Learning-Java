package com.student.services;

import java.util.ArrayList;
import java.util.List;

import com.student.models.Course;
import com.student.models.ICourse;
import com.student.utils.InputHelper;

public class CourseService extends BaseCourseService implements ICourseService {

    private final List<ICourse> courses = new ArrayList<>();
    private final InputHelper inputHelper = new InputHelper();

    @Override
    public void addCourse(String courseName, int creditHours) {
        ICourse course = new Course(courseName, creditHours);
        courses.add(course);
        System.out.println("Course added successfully: " + courseName);
    }

    @Override
    public List<ICourse> getAllCourses() {
        return courses;
    }

    @Override
    public void viewAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            for (ICourse course : courses) {
                System.out.println(course);
            }
        }
    }

    @Override
    public ICourse chooseCourse() {
        return inputHelper.chooseFromList(courses, "Choose a course:");
    }
}