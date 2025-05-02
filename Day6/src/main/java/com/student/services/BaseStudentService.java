package com.student.services;

import java.util.List;

import com.student.models.IStudent;

public abstract class BaseStudentService {

    public abstract void addStudent(String name, String email);

    public abstract List<IStudent> getAllStudents();

    public abstract IStudent chooseStudent();
}