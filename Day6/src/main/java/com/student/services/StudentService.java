package com.student.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.student.models.Course;
import com.student.models.ICourse;
import com.student.models.IStudent;
import com.student.models.Student;
import com.student.utils.DatabaseUtil;
import com.student.utils.InputHelper;

public class StudentService extends BaseStudentService implements IStudentService {

    private static final Logger LOGGER = Logger.getLogger(StudentService.class.getName());
    private final InputHelper inputHelper = new InputHelper();

    @Override
    public void addStudent(String name, String email) {
        String sql = "INSERT INTO students (name, email) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.executeUpdate();
            System.out.println("Student added successfully: " + name);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
    }

    @Override
    public List<IStudent> getAllStudents() {
        List<IStudent> students = new ArrayList<>();
        String sql = "SELECT s.id, s.name, s.email, c.course_name, c.credit_hours FROM students s " +
                     "LEFT JOIN student_courses sc ON s.id = sc.student_id " +
                     "LEFT JOIN courses c ON sc.course_id = c.id";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                // Check if the student already exists in the list
                Student student = (Student) students.stream()
                    .filter(s -> s.getId() == id)
                    .findFirst()
                    .orElse(null);

                if (student == null) {
                    student = new Student(id, name, email);
                    students.add(student);
                }

                // Add registered courses if available
                String courseName = resultSet.getString("course_name");
                int creditHours = resultSet.getInt("credit_hours");
                if (courseName != null) {
                    student.addCourse(new Course(courseName, creditHours));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
        return students;
    }

    @Override
    public void viewAllStudents() {
        List<IStudent> students = getAllStudents();
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
        List<IStudent> students = getAllStudents();
        return inputHelper.chooseFromList(students, "Choose a student:");
    }

    @Override
    public void addCourseToStudent(IStudent student, ICourse course) {
        String sql = "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, student.getId());
            statement.setInt(2, course.getCourseId());
            statement.executeUpdate();
            System.out.println("Course added successfully to student: " + student.getName());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
    }
}
