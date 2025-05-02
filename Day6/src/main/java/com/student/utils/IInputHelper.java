package com.student.utils;

import java.util.List;

public interface IInputHelper {
    int readInt(String message);
    double readDouble(String message);
    String readString(String message);
    <T> T chooseFromList(List<T> options, String message);
    boolean readYesNo(String message);
}