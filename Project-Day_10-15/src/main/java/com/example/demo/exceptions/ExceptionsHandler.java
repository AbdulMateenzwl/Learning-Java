package com.example.demo.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.utils.ApiResponseUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleUserNotFound(UsernameNotFoundException ex) {
        return ApiResponseUtil.notFound(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleUserNotFound(UserNotFoundException ex) {
        return ApiResponseUtil.notFound(ex.getMessage());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleTaskNotFound(TaskNotFoundException ex) {
        return ApiResponseUtil.notFound(ex.getMessage());
    }

    @ExceptionHandler(TaskAlreadyAssignedException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleTaskAlreadyAssigned(TaskAlreadyAssignedException ex) {
        return ApiResponseUtil.conflict(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleUnauthorizedOperation(UnauthorizedOperationException ex) {
        return ApiResponseUtil.unauthorized(ex.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ApiResponseDTO<String>> handleHttpClientUnauthorized(HttpClientErrorException.Unauthorized ex) {
        return ApiResponseUtil.unauthorized("Unauthorized access: " + ex.getMessage());
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleInvalidOperation(InvalidOperationException ex) {
        return ApiResponseUtil.badRequest(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Database constraint violation: " + ex.getMostSpecificCause().getMessage();
        return ApiResponseUtil.conflict(message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleEntityNotFound(EntityNotFoundException ex) {
        return ApiResponseUtil.notFound(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleConstraintViolation(ConstraintViolationException ex) {
        StringBuilder message = new StringBuilder("Validation failed: ");
        ex.getConstraintViolations().forEach(violation ->
                message.append(violation.getPropertyPath())
                        .append(": ")
                        .append(violation.getMessage())
                        .append(", "));
        return ApiResponseUtil.badRequest(message.substring(0, message.length() - 2));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDataAccessException(DataAccessException ex) {
        return ApiResponseUtil.internalServerError("Database error: " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleTransactionSystemException(TransactionSystemException ex) {
        Throwable cause = ex.getMostSpecificCause();
        if (cause instanceof ConstraintViolationException constraintViolationException) {
            return handleConstraintViolation(constraintViolationException);
        }
        return ApiResponseUtil.internalServerError("Transaction error: " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleJpaSystemException(JpaSystemException ex) {
        return ApiResponseUtil.internalServerError("Database operation error: " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ApiResponseDTO<String>> handlePersistenceException(PersistenceException ex) {
        return ApiResponseUtil.internalServerError("Database persistence error: " + ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleAccessDenied(AccessDeniedException ex) {
        return ApiResponseUtil.forbidden("Access denied: You don't have permission to perform this operation");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleBadCredentials(BadCredentialsException ex) {
        return ApiResponseUtil.unauthorized("Invalid credentials");
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleInsufficientAuthentication(InsufficientAuthenticationException ex) {
        return ApiResponseUtil.unauthorized("Authentication required");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleRuntimeException(RuntimeException ex) {
        return ApiResponseUtil.internalServerError(ex.getMessage());
    }
}
