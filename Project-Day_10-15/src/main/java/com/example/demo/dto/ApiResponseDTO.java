package com.example.demo.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class ApiResponseDTO<T> {
    private Instant timestamp;
    private int status;
    private boolean success;
    private String message;
    private T data;
}
