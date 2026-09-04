package com.dileep.studentmanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentResponse {

    private Integer rollNo;
    private String name;
    private String email;
    private String course;
    private String phone;

}