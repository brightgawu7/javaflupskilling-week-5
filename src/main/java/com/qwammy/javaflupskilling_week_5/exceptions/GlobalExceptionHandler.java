package com.qwammy.javaflupskilling_week_5.exceptions;

import com.qwammy.javaflupskilling_week_5.dtos.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling various exceptions throughout the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles custom application-specific exceptions.
     *
     * @param ex the custom application exception
     * @return a response entity with error details and appropriate status code
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(AppException ex) {
        ApiResponse<String> response = ex.formatError();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    /**
     * Handles validation exceptions for method arguments.
     *
     * @param ex the validation exception
     * @return a response entity with validation error details and HTTP status 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<Map<String, String>> response = buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation error occurred", errors, null);
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handles authentication exceptions.
     *
     * @param ex the authentication exception
     * @return a response entity with authentication error details and HTTP status 401
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Unauthorized");
        body.put("message", ex.getMessage());

        ApiResponse<Map<String, Object>> response = buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication error occurred", body, null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    
    /**
     * Handles generic exceptions.
     *
     * @param ex the generic exception
     * @return a response entity with error details and HTTP status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        ApiResponse<String> response = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Builds an error response.
     *
     * @param status  the HTTP status
     * @param message the error message
     * @param data    the data to include in the response
     * @param errors  the error details
     * @param <T>     the type of data
     * @return an ApiResponse object with the error details
     */
    private <T> ApiResponse<T> buildErrorResponse(HttpStatus status, String message, T data, String errors) {
        return ApiResponse.<T>builder()
                .success(false)
                .statusCode(status.value())
                .message(message)
                .data(data)
                .errors(errors)
                .build();
    }
}
