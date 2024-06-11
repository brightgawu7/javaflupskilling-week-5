package com.qwammy.javaflupskilling_week_5.dtos.auth.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SuccessfulLoginDto {
    private String accessToken;
}
