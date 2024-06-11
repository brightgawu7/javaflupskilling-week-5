package com.qwammy.javaflupskilling_week_5.dtos.auth.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {
    @NotEmpty(message = "email must not be empty")
    private String email;
    @NotEmpty(message = "password must not be empty")
    private String password;
    @NotEmpty(message = "first name must not be empty")
    private String firstName;
    @NotEmpty(message = "last name must not be empty")
    private String lastName;
}
