package com.student.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.student.models.Course;
import com.student.models.ICourse;
import com.student.utils.DatabaseUtil;
import com.student.utils.InputHelper;

public class CourseService extends BaseCourseService implements ICourseService {

    @Override
    public void addCourse(String courseName, int creditHours) {
        String sql = "INSERT INTO courses (course_name, credit_hours) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseName);
            statement.setInt(2, creditHours);
            statement.executeUpdate();
            System.out.println("Course added successfully: " + courseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ICourse> getAllCourses() {
        List<ICourse> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                int creditHours = resultSet.getInt("credit_hours");
                courses.add(new Course(courseName, creditHours));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public void viewAllCourses() {
        List<ICourse> courses = getAllCourses();
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
        List<ICourse> courses = getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses available to choose from.");
            return null;
        }

        for (int i = 0; i < courses.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + courses.get(i));
        }

        int choice = new InputHelper().readInt("Enter your choice: ");
        if (choice < 1 || choice > courses.size()) {
            System.out.println("Invalid choice.");
            return null;
        }

        return courses.get(choice - 1);
    }
}