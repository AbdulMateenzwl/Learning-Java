package com.example.demo.utils;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.ApiResponseUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class ApiResponseUtil {

    public static <T> ResponseEntity<ApiResponseDTO<T>> success(T data, String message) {
        ApiResponseDTO<T> responseDTO = new ApiResponseDTO<>();
        responseDTO.setTimestamp(Instant.now());
        responseDTO.setStatus(HttpStatus.OK.value());
        responseDTO.setSuccess(true);
        responseDTO.setMessage(message);
        responseDTO.setData(data);
        return ResponseEntity.ok(responseDTO);

    }
    public static <T> ResponseEntity<ApiResponseDTO<T>> created(T data, String message) {
        ApiResponseDTO<T> responseDTO = new ApiResponseDTO<>();
        responseDTO.setTimestamp(Instant.now());
        responseDTO.setStatus(HttpStatus.CREATED.value());
        responseDTO.setSuccess(true);
        responseDTO.setMessage(message);
        responseDTO.setData(data);
        return ResponseEntity.ok(responseDTO);
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> error(String message, HttpStatus statusCode) {
        ApiResponseDTO<T> responseDTO = new ApiResponseDTO<>();
        responseDTO.setTimestamp(Instant.now());
        responseDTO.setStatus(statusCode.value());
        responseDTO.setSuccess(false);
        responseDTO.setMessage(message);
        return ResponseEntity.ok(responseDTO);
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> notFound(String message) {
        return error(message, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> badRequest(String message) {
        return error(message, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> unauthorized(String message) {
        return error(message, HttpStatus.UNAUTHORIZED);
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> forbidden(String message) {
        return error(message, HttpStatus.FORBIDDEN);
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> internalServerError(String message) {
        return error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> conflict(String message) {
        return error(message, HttpStatus.CONFLICT);
    }


}
