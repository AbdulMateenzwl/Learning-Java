package com.student.services;

import com.student.models.ICourse;
import com.student.models.IStudent;

public interface IStudentService {
    void addStudent(String name, String email);
    IStudent chooseStudent();
    void viewAllStudents();
    void addCourseToStudent(IStudent student, ICourse course);
}
