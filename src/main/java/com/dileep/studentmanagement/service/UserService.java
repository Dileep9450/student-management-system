package com.dileep.studentmanagement.service;

import com.dileep.studentmanagement.dto.RegisterRequest;
import com.dileep.studentmanagement.entity.User;
import com.dileep.studentmanagement.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.dileep.studentmanagement.dto.RegisterResponse;
import com.dileep.studentmanagement.exception.DuplicateUsernameException;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegisterResponse registerUser(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {

            throw new DuplicateUsernameException("Username already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("USER");

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }
}