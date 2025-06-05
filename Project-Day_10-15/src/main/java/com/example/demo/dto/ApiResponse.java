package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ApiResponse<T> {
    private Instant timestamp;
    private int status;
    private boolean success;
    private String message;
    private T data;
    private String errors;
}
