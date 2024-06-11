package com.qwammy.javaflupskilling_week_5.options;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "admin-options")
public class AdminOptions {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
