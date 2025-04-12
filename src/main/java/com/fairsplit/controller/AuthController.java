package com.fairsplit.controller;

import com.fairsplit.dto.AuthResponse;
import com.fairsplit.dto.LoginRequest;
import com.fairsplit.dto.UserRegistrationRequest;
import com.fairsplit.dto.UserResponse;
import com.fairsplit.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRegistrationRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

}
