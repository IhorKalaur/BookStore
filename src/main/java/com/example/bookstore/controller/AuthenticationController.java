package com.example.bookstore.controller;

import com.example.bookstore.dto.user.UserLoginRequestDto;
import com.example.bookstore.dto.user.UserLoginResponseDto;
import com.example.bookstore.dto.user.UserRegistrationRequestDto;
import com.example.bookstore.dto.user.UserRegistrationResponseDto;
import com.example.bookstore.exceptions.RegistrationException;
import com.example.bookstore.security.AuthenticationService;
import com.example.bookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication and Registration",
        description = "Authenticate and register new users")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user",
            description = "Registration of a new user with the specified information"
    )
    public UserRegistrationResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "User Login",
            description = "Authenticate a user by email and password"
    )
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
