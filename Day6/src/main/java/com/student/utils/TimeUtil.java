package com.student.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    // Get current date & time
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    // Format any LocalDateTime
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(dateTimeFormatter);
    }
}