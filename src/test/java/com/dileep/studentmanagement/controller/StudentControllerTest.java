package com.dileep.studentmanagement.controller;

import com.dileep.studentmanagement.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.dileep.studentmanagement.dto.StudentResponse;
import org.mockito.Mockito;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.dileep.studentmanagement.dto.StudentRequest;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import com.dileep.studentmanagement.dto.StudentRequest;
import com.dileep.studentmanagement.dto.StudentResponse;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.dileep.studentmanagement.security.JwtService;


@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetAllStudents() throws Exception {

        mockMvc.perform(get("/students")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "rollNo")
                        .param("direction", "asc"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetStudentByRollNo() throws Exception {

        StudentResponse response = new StudentResponse();
        response.setRollNo(1);
        response.setName("Dileep");
        response.setEmail("dileep@gmail.com");
        response.setCourse("B.Tech");
        response.setPhone("9876543210");

        Mockito.when(studentService.getStudentByRollNo(1))
                .thenReturn(response);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rollNo").value(1))
                .andExpect(jsonPath("$.name").value("Dileep"))
                .andExpect(jsonPath("$.email").value("dileep@gmail.com"))
                .andExpect(jsonPath("$.course").value("B.Tech"))
                .andExpect(jsonPath("$.phone").value("9876543210"));
    }
    @Test
    void testSaveStudent() throws Exception {

        StudentRequest request = new StudentRequest();

        request.setName("Dileep");
        request.setEmail("newdileep@gmail.com");
        request.setCourse("B.Tech");
        request.setPhone("9876543210");

        StudentResponse response = new StudentResponse();

        response.setRollNo(10);
        response.setName("Dileep");
        response.setEmail("newdileep@gmail.com");
        response.setCourse("B.Tech");
        response.setPhone("9876543210");

        Mockito.when(studentService.saveStudent(Mockito.any(StudentRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rollNo").value(10))
                .andExpect(jsonPath("$.name").value("Dileep"))
                .andExpect(jsonPath("$.email").value("newdileep@gmail.com"))
                .andExpect(jsonPath("$.course").value("B.Tech"))
                .andExpect(jsonPath("$.phone").value("9876543210"));
    }
    @Test
    void testUpdateStudent() throws Exception {

        StudentRequest request = new StudentRequest();

        request.setName("Updated Dileep");
        request.setEmail("updated@gmail.com");
        request.setCourse("Spring Boot");
        request.setPhone("9876543210");


        StudentResponse response = new StudentResponse();

        response.setRollNo(1);
        response.setName("Updated Dileep");
        response.setEmail("updated@gmail.com");
        response.setCourse("Spring Boot");
        response.setPhone("9876543210");


        Mockito.when(studentService.updateStudent(
                        Mockito.eq(1),
                        Mockito.any(StudentRequest.class)))
                .thenReturn(response);


        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rollNo").value(1))
                .andExpect(jsonPath("$.name").value("Updated Dileep"))
                .andExpect(jsonPath("$.email").value("updated@gmail.com"))
                .andExpect(jsonPath("$.course").value("Spring Boot"))
                .andExpect(jsonPath("$.phone").value("9876543210"));
    }
    @Test
    void testDeleteStudent() throws Exception {

        Mockito.when(studentService.deleteStudent(1))
                .thenReturn("Student Deleted Successfully");

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student Deleted Successfully"));
    }
    @Test
    void testGetStudentByEmail() throws Exception {

        StudentResponse response = new StudentResponse();

        response.setRollNo(1);
        response.setName("Dileep");
        response.setEmail("dileep@gmail.com");
        response.setCourse("B.Tech");
        response.setPhone("9876543210");

        Mockito.when(studentService.getStudentByEmail("dileep@gmail.com"))
                .thenReturn(response);

        mockMvc.perform(get("/students/email/dileep@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rollNo").value(1))
                .andExpect(jsonPath("$.name").value("Dileep"))
                .andExpect(jsonPath("$.email").value("dileep@gmail.com"))
                .andExpect(jsonPath("$.course").value("B.Tech"))
                .andExpect(jsonPath("$.phone").value("9876543210"));
    }

}