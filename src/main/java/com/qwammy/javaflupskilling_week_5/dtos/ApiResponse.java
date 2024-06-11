package com.qwammy.javaflupskilling_week_5.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiResponse<T> {
    private boolean success;
    private int statusCode;
    private String message = "";
    private T data;
    private Object errors;

    public static <T> ApiResponse<T> success(T data, String message, int statusCode) {

        return ApiResponse.<T>builder().success(true).data(data).message(message).statusCode(statusCode).build();
    }
}
