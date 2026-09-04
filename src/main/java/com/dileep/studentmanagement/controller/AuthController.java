package com.dileep.studentmanagement.controller;

import com.dileep.studentmanagement.dto.LoginRequest;
import com.dileep.studentmanagement.dto.LoginResponse;
import com.dileep.studentmanagement.dto.RegisterRequest;
import com.dileep.studentmanagement.dto.RegisterResponse;
import com.dileep.studentmanagement.service.LoginService;
import com.dileep.studentmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final LoginService loginService;

    public AuthController(
            UserService userService,
            LoginService loginService) {

        this.userService = userService;
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = userService.registerUser(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = loginService.login(request);

        return ResponseEntity.ok(response);
    }
}