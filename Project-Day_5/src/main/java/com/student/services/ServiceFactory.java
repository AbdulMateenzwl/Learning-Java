package com.student.services;

public class ServiceFactory {

    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();

    public static StudentService getStudentService() {
        return studentService;
    }

    public static CourseService getCourseService() {
        return courseService;
    }
}