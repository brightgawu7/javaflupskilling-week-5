package com.qwammy.javaflupskilling_week_5.exceptions.auth;

import com.qwammy.javaflupskilling_week_5.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends AppException {

    public UserAlreadyExistsException() {
        this("User with this email already exists");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
        setStatusCode(HttpStatus.BAD_REQUEST.value());
    }

}
