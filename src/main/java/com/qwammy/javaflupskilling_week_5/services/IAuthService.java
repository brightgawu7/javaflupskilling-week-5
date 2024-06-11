package com.qwammy.javaflupskilling_week_5.services;

import com.qwammy.javaflupskilling_week_5.dtos.auth.request.LoginDto;
import com.qwammy.javaflupskilling_week_5.dtos.auth.request.SuccessfulLoginDto;
import com.qwammy.javaflupskilling_week_5.dtos.auth.request.UserRegistrationDto;
import com.qwammy.javaflupskilling_week_5.dtos.auth.response.CustomUserReadDto;
import com.qwammy.javaflupskilling_week_5.exceptions.auth.UserAlreadyExistsException;

public interface IAuthService {
    SuccessfulLoginDto login(LoginDto dto);

    CustomUserReadDto registerUser(UserRegistrationDto userRegistrationDto) throws UserAlreadyExistsException;
}
