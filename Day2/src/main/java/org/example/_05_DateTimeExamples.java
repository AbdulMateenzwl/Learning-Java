package org.example;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class _05_DateTimeExamples {
    public static void main(String[] args) {
        //  LocalDate – date without time
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(2004, Month.NOVEMBER, 29);
        System.out.println("Today's Date: " + today);
        System.out.println("My Birthday: " + birthday);

        //  LocalTime – time without date
        LocalTime nowTime = LocalTime.now();
        LocalTime classTime = LocalTime.of(9, 30);
        System.out.println("Current Time: " + nowTime);
        System.out.println("Class Time: " + classTime);

        //  LocalDateTime – date and time together
        LocalDateTime meeting = LocalDateTime.of(2025, 4, 15, 14, 0);
        System.out.println("Meeting Time: " + meeting);

        //  ZonedDateTime – date and time with time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("Zoned DateTime: " + zonedDateTime);

        //  Formatting and Parsing
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = meeting.format(formatter);
        System.out.println("Formatted Meeting DateTime: " + formattedDate);

        //  Parsing a String into DateTime
        String dateStr = "20-10-2024 08:30:00";
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateStr, formatter);
        System.out.println("Parsed DateTime: " + parsedDateTime);

        //  Period – used for date-based amounts of time
        Period period = Period.between(birthday, today);
        System.out.println("Age: " + period.getYears() + " years, " + period.getMonths() + " months");

        //  Duration – used for time-based amounts
        Duration duration = Duration.between(classTime, nowTime);
        System.out.println("Duration since class started: " + duration.toMinutes() + " minutes");

        //  ChronoUnit – for calculating differences
        long daysBetween = ChronoUnit.DAYS.between(birthday, today);
        System.out.println("Days since birth: " + daysBetween + " days");

        //  Adding/Subtracting Dates
        LocalDate nextWeek = today.plusWeeks(1);
        LocalTime tenMinutesLater = nowTime.plusMinutes(10);
        System.out.println("Date after one week: " + nextWeek);
        System.out.println("Time after 10 minutes: " + tenMinutesLater);
    }
}
