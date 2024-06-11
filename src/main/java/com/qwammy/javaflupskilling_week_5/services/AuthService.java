package com.qwammy.javaflupskilling_week_5.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qwammy.javaflupskilling_week_5.dtos.auth.request.LoginDto;
import com.qwammy.javaflupskilling_week_5.dtos.auth.request.SuccessfulLoginDto;
import com.qwammy.javaflupskilling_week_5.dtos.auth.request.UserRegistrationDto;
import com.qwammy.javaflupskilling_week_5.dtos.auth.response.CustomUserReadDto;
import com.qwammy.javaflupskilling_week_5.entities.CustomUser;
import com.qwammy.javaflupskilling_week_5.entities.Role;
import com.qwammy.javaflupskilling_week_5.enums.UserRoles;
import com.qwammy.javaflupskilling_week_5.exceptions.auth.UserAlreadyExistsException;
import com.qwammy.javaflupskilling_week_5.repositories.CustomUserRepository;
import com.qwammy.javaflupskilling_week_5.repositories.RoleRepository;
import com.qwammy.javaflupskilling_week_5.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Primary
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final CustomUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, CustomUserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    @Override
    public SuccessfulLoginDto login(LoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        return SuccessfulLoginDto
                .builder()
                .accessToken(token)
                .build();
    }

    @Override
    public CustomUserReadDto registerUser(UserRegistrationDto userRegistrationDto) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(userRegistrationDto.getEmail()) != null) {
            throw new UserAlreadyExistsException();
        }

        Role adminRole = roleRepository.findByRole(UserRoles.CLIENT);

        CustomUser user = objectMapper.convertValue(userRegistrationDto, CustomUser.class);
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setRoles(Collections.singletonList(adminRole));

        return objectMapper.convertValue(userRepository.save(user), CustomUserReadDto.class);

    }
}
