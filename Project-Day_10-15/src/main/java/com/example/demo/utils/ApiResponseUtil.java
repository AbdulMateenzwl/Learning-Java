package com.example.demo.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class ApiResponseUtil {

    public static <T> ResponseEntity<T> success(T data, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Timestamp", Instant.now().toString());
        headers.set("X-Status", String.valueOf(HttpStatus.OK.value()));
        headers.set("X-Success", "true");
        headers.set("X-Message", message);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }


    public static <T> ResponseEntity<T> created(T data, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Timestamp", Instant.now().toString());
        headers.set("X-Status", String.valueOf(HttpStatus.CREATED.value()));
        headers.set("X-Success", "true");
        headers.set("X-Message", message);

        return new ResponseEntity<>(data, headers, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<T> error(String message, HttpStatus statusCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Timestamp", Instant.now().toString());
        headers.set("X-Status", String.valueOf(statusCode.value()));
        headers.set("X-Success", "false");
        headers.set("X-Message", message);

        return new ResponseEntity<>(null, headers, statusCode);
    }

    public static <T> ResponseEntity<T> notFound(String message) {
        return error(message, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<T> badRequest(String message) {
        return error(message, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<T> unauthorized(String message) {
        return error(message, HttpStatus.UNAUTHORIZED);
    }

    public static <T> ResponseEntity<T> forbidden(String message) {
        return error(message, HttpStatus.FORBIDDEN);
    }

    public static <T> ResponseEntity<T> internalServerError(String message) {
        return error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<T> conflict(String message) {
        return error(message, HttpStatus.CONFLICT);
    }


}
