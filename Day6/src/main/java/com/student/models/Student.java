package com.student.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.student.utils.DatabaseUtil;

public class Student implements IStudent {

    private static final Logger LOGGER = Logger.getLogger(Student.class.getName());

    private int id; // Add an ID field
    private String name;
    private String email;
    private List<ICourse> enrolledCourses;
    private List<ICourse> registeredCourses;

    // Constructor to initialize ID, name, and email
    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.enrolledCourses = new ArrayList<>();
    }

    // Constructor
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
        this.enrolledCourses = new ArrayList<>();
    }

    // Getters and Setters
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<ICourse> getEnrolledCourses() {
        return enrolledCourses;
    }

    @Override
    public void setEnrolledCourses(List<ICourse> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public void addCourse(ICourse course) {
        if (course != null && !enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    @Override
    public void removeCourse(ICourse course) {
        enrolledCourses.remove(course);
    }

    @Override
    public void displayEnrolledCourses() {
        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses enrolled.");
        } else {
            System.out.println(name + "'s Enrolled Courses:");
            for (ICourse course : enrolledCourses) {
                System.out.println(course);
            }
        }
    }

    public List<ICourse> getRegisteredCourses() {
        if (registeredCourses == null) {
            registeredCourses = new ArrayList<>();
            String sql = "SELECT c.course_name, c.credit_hours FROM courses c " +
                         "JOIN student_courses sc ON c.id = sc.course_id WHERE sc.student_id = ?";
            try (Connection connection = DatabaseUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, this.getId());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String courseName = resultSet.getString("course_name");
                    int creditHours = resultSet.getInt("credit_hours");
                    registeredCourses.add(new Course(courseName, creditHours));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Database error while fetching registered courses", e);
            }
        }
        return registeredCourses;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', email='" + email + "', enrolledCourses=" + enrolledCourses + "}";
    }
}
