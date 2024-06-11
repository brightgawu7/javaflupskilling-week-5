package com.qwammy.javaflupskilling_week_5.exceptions;

import com.qwammy.javaflupskilling_week_5.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a base class for application exceptions.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppException extends Exception {

    private Object errors = "";
    private int statusCode = 400; // Default status code for bad request

    /**
     * Initializes a new instance of the {@code AppException} class with a specified error message.
     *
     * @param message The message that describes the error.
     */
    protected AppException(String message) {
        super(message);
    }

    /**
     * Formats the error details into an {@code ApiResponse} object.
     *
     * @return An {@code ApiResponse} object containing the error details.
     */
    public ApiResponse<String> formatError() {
        return ApiResponse.<String>builder()
                .success(false)
                .statusCode(statusCode)
                .message(getMessage())
                .errors(errors)
                .build();
    }
}
