package com.qwammy.javaflupskilling_week_5.dtos.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserReadDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
}
