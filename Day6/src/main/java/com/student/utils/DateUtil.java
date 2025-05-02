package com.student.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Get current date
    public static String getCurrentDate() {
        return LocalDate.now().format(dateFormatter);
    }

    // Format any LocalDate
    public static String formatDate(LocalDate date) {
        return date.format(dateFormatter);
    }
}