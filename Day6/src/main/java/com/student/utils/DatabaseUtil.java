package com.student.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    private static final String URL = "jdbc:h2:~/student_management;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        String schemaFile = "src/main/resources/schema.sql";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            // Drop all existing tables
            statement.execute("DROP ALL OBJECTS");

            // Read and execute the schema.sql script
            try (BufferedReader reader = new BufferedReader(new FileReader(schemaFile))) {
                StringBuilder sql = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sql.append(line).append("\n");
                }
                statement.execute(sql.toString());
            }

            System.out.println("Database initialized successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}