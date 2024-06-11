package com.qwammy.javaflupskilling_week_5.controllers;

import com.qwammy.javaflupskilling_week_5.dtos.ApiResponse;
import com.qwammy.javaflupskilling_week_5.dtos.auth.request.LoginDto;
import com.qwammy.javaflupskilling_week_5.dtos.auth.request.SuccessfulLoginDto;
import com.qwammy.javaflupskilling_week_5.dtos.auth.request.UserRegistrationDto;
import com.qwammy.javaflupskilling_week_5.dtos.auth.response.CustomUserReadDto;
import com.qwammy.javaflupskilling_week_5.exceptions.auth.UserAlreadyExistsException;
import com.qwammy.javaflupskilling_week_5.services.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }


    @PostMapping("login")
    public ResponseEntity<ApiResponse<SuccessfulLoginDto>> login(@RequestBody LoginDto loginDto) {

        ApiResponse<SuccessfulLoginDto> response = ApiResponse
                .success(
                        authService.login(loginDto),
                        "Successfully logged in",
                        HttpStatus.OK.value()
                );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse<CustomUserReadDto>> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) throws UserAlreadyExistsException {

        ApiResponse<CustomUserReadDto> response = ApiResponse
                .success(
                        authService.registerUser(userRegistrationDto),
                        "User created successfully",
                        HttpStatus.CREATED.value()
                );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
