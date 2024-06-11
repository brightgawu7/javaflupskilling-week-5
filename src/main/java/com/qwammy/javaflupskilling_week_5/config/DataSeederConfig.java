package com.qwammy.javaflupskilling_week_5.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qwammy.javaflupskilling_week_5.entities.CustomUser;
import com.qwammy.javaflupskilling_week_5.entities.Role;
import com.qwammy.javaflupskilling_week_5.enums.UserRoles;
import com.qwammy.javaflupskilling_week_5.options.AdminOptions;
import com.qwammy.javaflupskilling_week_5.repositories.CustomUserRepository;
import com.qwammy.javaflupskilling_week_5.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class DataSeederConfig {

    private final CustomUserRepository customUserRepository;
    private final RoleRepository roleRepository;
    private final AdminOptions adminOptions;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public DataSeederConfig(CustomUserRepository customUserRepository, RoleRepository roleRepository, AdminOptions adminOptions, PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.customUserRepository = customUserRepository;
        this.roleRepository = roleRepository;
        this.adminOptions = adminOptions;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    /**
     * A CommandLineRunner bean to seed initial data.
     *
     * @return A CommandLineRunner object.
     */
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            seedRoles();
            seedAdminUser();
        };
    }

    /**
     * Seed roles if they don't exist.
     */
    private void seedRoles() {
        seedRoleIfNotExist(UserRoles.ADMIN);
        seedRoleIfNotExist(UserRoles.CLIENT);
    }

    /**
     * Seed a role if it doesn't exist.
     *
     * @param roleName The role name.
     */
    private void seedRoleIfNotExist(UserRoles roleName) {
        Role role = roleRepository.findByRole(roleName);
        if (role == null) {
            role = Role.builder().role(roleName).build();
            roleRepository.save(role);
        }
    }

    /**
     * Seed admin user if not exists.
     */
    private void seedAdminUser() {
        if (customUserRepository.findByEmail(adminOptions.getEmail()) == null) {
            Role adminRole = roleRepository.findByRole(UserRoles.ADMIN);
            if (adminRole == null) {
                throw new IllegalStateException("Admin role not found.");
            }
            
            CustomUser adminUser = objectMapper.convertValue(adminOptions, CustomUser.class);
            adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
            adminUser.setRoles(Collections.singletonList(adminRole));

            customUserRepository.save(adminUser);
        }
    }

}
