package com.dileep.studentmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
@Getter
@Setter
@NoArgsConstructor
public class StudentRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Course cannot be empty")
    private String course;

    @Pattern(regexp = "\\d{10}", message = "Phone number must contain exactly 10 digits")
    private String phone;

    }