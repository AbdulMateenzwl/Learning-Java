package com.example.demo.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private Instant timestamp;
    private int status;
    private boolean success;
    private String message;
    private T data;
}
