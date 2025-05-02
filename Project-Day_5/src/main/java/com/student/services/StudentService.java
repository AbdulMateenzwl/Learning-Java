package com.student.services;

import java.util.ArrayList;
import java.util.List;

import com.student.models.ICourse;
import com.student.models.IStudent;
import com.student.models.Student;
import com.student.utils.InputHelper;

public class StudentService extends BaseStudentService implements IStudentService {

    private final List<IStudent> students = new ArrayList<>();
    private final InputHelper inputHelper = new InputHelper();

    @Override
    public void addStudent(String name, String email) {
        IStudent student = new Student(name, email);
        students.add(student);
        System.out.println("Student added successfully: " + name);
    }

    @Override
    public List<IStudent> getAllStudents() {
        return students;
    }

    @Override
    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (IStudent student : students) {
                System.out.println(student);
            }
        }
    }

    @Override
    public IStudent chooseStudent() {
        return inputHelper.chooseFromList(students, "Choose a student:");
    }

    @Override
    public void addCourseToStudent(IStudent student, ICourse course) {
        if (student.getEnrolledCourses().contains(course)) {
            System.out.println("Student is already enrolled in this course.");
        } else {
            student.addCourse(course);
            System.out.println("Course added successfully to student: " + student.getName());
        }
    }
}
