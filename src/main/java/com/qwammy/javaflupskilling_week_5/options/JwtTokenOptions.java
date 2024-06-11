package com.qwammy.javaflupskilling_week_5.options;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt-token-options"
)
public class JwtTokenOptions {
    private String secret;
    private int jwtExpirationMs;
}
