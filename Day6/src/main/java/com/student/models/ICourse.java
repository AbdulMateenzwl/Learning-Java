package com.student.models;

import java.time.LocalDate;

public interface ICourse {
    int getCourseId();
    String getCourseName();
    void setCourseName(String courseName);
    int getCreditHours();
    void setCreditHours(int creditHours);
    LocalDate getCreatedOn();
    void setCreatedOn(LocalDate createdOn);
}