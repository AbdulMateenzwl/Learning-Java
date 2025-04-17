package com.student.exceptions;

// Custom Exception Class for handling student not found scenarios
public class StudentNotFoundException extends Exception {

    // Constructor that takes a message
    public StudentNotFoundException(String message) {
        super(message); // Pass the message to the parent Exception class
    }

    // Constructor that takes a message and a cause (another exception)
    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause); // Pass the message and cause to the parent Exception class
    }
}
