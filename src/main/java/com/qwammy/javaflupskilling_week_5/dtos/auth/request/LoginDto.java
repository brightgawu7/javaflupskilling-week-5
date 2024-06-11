package com.qwammy.javaflupskilling_week_5.dtos.auth.request;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
