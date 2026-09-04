package com.dileep.studentmanagement;

    import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
    import com.dileep.studentmanagement.dto.StudentRequest;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
    import org.springframework.http.MediaType;
    import org.springframework.test.web.servlet.MockMvc;
    import com.fasterxml.jackson.databind.ObjectMapper;

    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
    import com.dileep.studentmanagement.repository.StudentRepository;
    import com.dileep.studentmanagement.entity.Student;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import java.util.UUID;
    import com.dileep.studentmanagement.dto.StudentRequest;
    import com.dileep.studentmanagement.dto.StudentResponse;

    import com.fasterxml.jackson.databind.ObjectMapper;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.http.MediaType;
    import org.springframework.test.web.servlet.MockMvc;
    import org.springframework.test.web.servlet.MvcResult;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;

    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
    import com.dileep.studentmanagement.dto.LoginRequest;
    import com.dileep.studentmanagement.dto.LoginResponse;
    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "jwt.secret=DileepStudentManagementSystemSecretKey2026")
@AutoConfigureMockMvc
class StudentIntegrationTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private StudentRepository studentRepository;

        private String getAdminToken() throws Exception {

            LoginRequest loginRequest = new LoginRequest();

            loginRequest.setUsername("admin");
            loginRequest.setPassword("admin123");

            MvcResult result = mockMvc.perform(
                            post("/auth/login")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andReturn();

            LoginResponse loginResponse =
                    objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            LoginResponse.class
                    );

            return loginResponse.getToken();
        }

        @Test
        void contextLoads() {
        }
        @Test
        void testCreateStudent() throws Exception {

            String email = "integration-" + UUID.randomUUID() + "@gmail.com";

            StudentRequest request = new StudentRequest();

            request.setName("Integration Dileep");
            request.setEmail(email);
            request.setCourse("Spring Boot");
            request.setPhone("9876543210");

            String token = getAdminToken();

            mockMvc.perform(
                            post("/students")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isOk());

            Student savedStudent = studentRepository
                    .findByEmail(email)
                    .orElseThrow();

            assertEquals("Integration Dileep", savedStudent.getName());
            assertEquals(email, savedStudent.getEmail());
            assertEquals("Spring Boot", savedStudent.getCourse());
            assertEquals("9876543210", savedStudent.getPhone());
        }
    @Test
    void testGetStudentByRollNo() throws Exception {

        // Create a unique email so the test can run multiple times
        String email = "get-test-" + java.util.UUID.randomUUID() + "@gmail.com";

        // Create student request
        StudentRequest request = new StudentRequest();
        request.setName("Get Test Student");
        request.setEmail(email);
        request.setCourse("Java");
        request.setPhone("9876543210");

        // Get ADMIN JWT token
        String token = getAdminToken();

        // Create student
        MvcResult createResult = mockMvc.perform(
                        post("/students")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andReturn();

        // Convert response JSON into StudentResponse
        StudentResponse createdStudent =
                objectMapper.readValue(
                        createResult.getResponse().getContentAsString(),
                        StudentResponse.class
                );

        // Get generated roll number
        Integer rollNo = createdStudent.getRollNo();

        // Get student by roll number
        mockMvc.perform(
                        get("/students/" + rollNo)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rollNo").value(rollNo))
                .andExpect(jsonPath("$.name").value("Get Test Student"))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.course").value("Java"))
                .andExpect(jsonPath("$.phone").value("9876543210"));
    }
        @Test
        void testUpdateStudent() throws Exception {

            // 1. Create a student first
            String email = "update-test-" + java.util.UUID.randomUUID() + "@gmail.com";

            StudentRequest createRequest = new StudentRequest();
            createRequest.setName("Old Name");
            createRequest.setEmail(email);
            createRequest.setCourse("Java");
            createRequest.setPhone("9876543210");

            String token = getAdminToken();

            // 2. Create student using ADMIN token
            MvcResult createResult = mockMvc.perform(
                            post("/students")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(createRequest))
                    )
                    .andExpect(status().isOk())
                    .andReturn();

            StudentResponse createdStudent =
                    objectMapper.readValue(
                            createResult.getResponse().getContentAsString(),
                            StudentResponse.class
                    );

            Integer rollNo = createdStudent.getRollNo();

            // 3. Prepare update request
            StudentRequest updateRequest = new StudentRequest();
            updateRequest.setName("Updated Dileep");
            updateRequest.setEmail(email);
            updateRequest.setCourse("Advanced Spring Boot");
            updateRequest.setPhone("9999999999");

            // 4. Update student using ADMIN token
            mockMvc.perform(
                            put("/students/" + rollNo)
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(updateRequest))
                    )
                    .andExpect(status().isOk());
        }
    @Test
    void testDeleteStudent() throws Exception {

        // 1. Create a unique email
        String email = "delete-test-" + java.util.UUID.randomUUID() + "@gmail.com";

        // 2. Create student request
        StudentRequest request = new StudentRequest();
        request.setName("Delete Test Student");
        request.setEmail(email);
        request.setCourse("Java");
        request.setPhone("9876543210");

        // 3. Get ADMIN JWT token
        String token = getAdminToken();

        // 4. Create the student
        MvcResult createResult = mockMvc.perform(
                        post("/students")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andReturn();

        // 5. Convert JSON response to StudentResponse
        StudentResponse createdStudent =
                objectMapper.readValue(
                        createResult.getResponse().getContentAsString(),
                        StudentResponse.class
                );

        // 6. Get generated roll number
        Integer rollNo = createdStudent.getRollNo();

        // 7. Delete the student using ADMIN JWT
        mockMvc.perform(
                        delete("/students/" + rollNo)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk());

        // 8. Verify student no longer exists in database
        assertTrue(studentRepository.findById(rollNo).isEmpty());
    }
    @Test
    void testDuplicateEmail() throws Exception {

        // Get ADMIN JWT token
        String token = getAdminToken();

        // Create first student request
        String email = "duplicate-" + java.util.UUID.randomUUID() + "@gmail.com";

        StudentRequest request = new StudentRequest();
        request.setName("Duplicate Test Student");
        request.setEmail(email);
        request.setCourse("Java");
        request.setPhone("9876543210");

        // First POST - should successfully create student
        mockMvc.perform(
                        post("/students")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

        // Second POST with the same email
        StudentRequest duplicateRequest = new StudentRequest();
        duplicateRequest.setName("Another Student");
        duplicateRequest.setEmail(email);
        duplicateRequest.setCourse("Python");
        duplicateRequest.setPhone("9999999999");

        // Should fail because email already exists
        mockMvc.perform(
                        post("/students")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(duplicateRequest))
                )
                .andExpect(status().isBadRequest());
    }
        @Test
        void testValidationError() throws Exception {

            StudentRequest request = new StudentRequest();

            request.setName("");
            request.setEmail("invalid-email");
            request.setCourse("");
            request.setPhone("123");

            String token = getAdminToken();

            mockMvc.perform(
                            post("/students")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.message").value("Validation Failed"))
                    .andExpect(jsonPath("$.errors.name").value("Name cannot be empty"))
                    .andExpect(jsonPath("$.errors.email").value("Enter a valid email"))
                    .andExpect(jsonPath("$.errors.course").value("Course cannot be empty"))
                    .andExpect(jsonPath("$.errors.phone")
                            .value("Phone number must contain exactly 10 digits"));
        }
    }

