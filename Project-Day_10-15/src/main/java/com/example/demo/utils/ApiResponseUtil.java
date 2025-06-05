package com.example.demo.utils;

import com.example.demo.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class ApiResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .success(true)
                .message(message)
                .data(data)
                .errors(null)
                .build());
    }
    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return ResponseEntity.ok(ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CREATED.value())
                .success(true)
                .message(message)
                .data(data)
                .errors(null)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String message, int statusCode) {
        return ResponseEntity.status(statusCode).body(ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(statusCode)
                .success(false)
                .message(message)
                .data(null)
                .errors(null)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return error(message, HttpStatus.NOT_FOUND.value());
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        return error(message, HttpStatus.BAD_REQUEST.value());
    }

    public static <T> ResponseEntity<ApiResponse<T>> unauthorized(String message) {
        return error(message, HttpStatus.UNAUTHORIZED.value());
    }

    public static <T> ResponseEntity<ApiResponse<T>> forbidden(String message) {
        return error(message, HttpStatus.FORBIDDEN.value());
    }

    public static <T> ResponseEntity<ApiResponse<T>> internalServerError(String message) {
        return error(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static <T> ResponseEntity<ApiResponse<T>> conflict(String message) {
        return error(message, HttpStatus.CONFLICT.value());
    }


}
