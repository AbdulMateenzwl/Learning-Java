package com.student.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    // Static formatter
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    // Get current date
    public static String getCurrentDate() {
        return LocalDate.now().format(dateFormatter);
    }

    // Get current date & time
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    // Format any LocalDate
    public static String formatDate(LocalDate date) {
        return date.format(dateFormatter);
    }

    // Format any LocalDateTime
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(dateTimeFormatter);
    }
}
