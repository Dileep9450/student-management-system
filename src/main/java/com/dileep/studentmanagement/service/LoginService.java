package com.dileep.studentmanagement.service;

import com.dileep.studentmanagement.dto.LoginRequest;
import com.dileep.studentmanagement.dto.LoginResponse;
import com.dileep.studentmanagement.entity.User;
import com.dileep.studentmanagement.repository.UserRepository;
import com.dileep.studentmanagement.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.dileep.studentmanagement.exception.InvalidCredentialsException;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginService(
            UserRepository userRepository,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );
        return new LoginResponse(token);
    }
}
