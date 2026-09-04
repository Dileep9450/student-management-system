package com.dileep.studentmanagement.service;

import com.dileep.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.dileep.studentmanagement.dto.StudentRequest;
import com.dileep.studentmanagement.dto.StudentResponse;
import com.dileep.studentmanagement.entity.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.dileep.studentmanagement.exception.ResourceNotFoundException;
import com.dileep.studentmanagement.exception.DuplicateEmailException;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testMockitoSetup() {

        System.out.println("Mockito setup successful.");

    }
    @Test
    void testSaveStudent() {

        StudentRequest request = new StudentRequest();
        request.setName("Dileep");
        request.setEmail("dileep@gmail.com");
        request.setCourse("Spring Boot");
        request.setPhone("9876543210");

        Student savedStudent = new Student();
        savedStudent.setRollNo(1);
        savedStudent.setName("Dileep");
        savedStudent.setEmail("dileep@gmail.com");
        savedStudent.setCourse("Spring Boot");
        savedStudent.setPhone("9876543210");

        when(studentRepository.save(org.mockito.ArgumentMatchers.any(Student.class)))
                .thenReturn(savedStudent);

        StudentResponse response = studentService.saveStudent(request);

        assertNotNull(response);
        assertEquals("Dileep", response.getName());
        assertEquals("dileep@gmail.com", response.getEmail());
        assertEquals(1, response.getRollNo());

        verify(studentRepository).save(org.mockito.ArgumentMatchers.any(Student.class));
    }
    @Test
    void testGetStudentByRollNo() {

        Student student = new Student();
        student.setRollNo(1);
        student.setName("Dileep");
        student.setEmail("dileep@gmail.com");
        student.setCourse("Spring Boot");
        student.setPhone("9876543210");

        when(studentRepository.findById(1))
                .thenReturn(Optional.of(student));

        StudentResponse response = studentService.getStudentByRollNo(1);

        assertNotNull(response);
        assertEquals(1, response.getRollNo());
        assertEquals("Dileep", response.getName());
        assertEquals("dileep@gmail.com", response.getEmail());

        verify(studentRepository).findById(1);
    }
    @Test
    void testUpdateStudent() {

        StudentRequest request = new StudentRequest();
        request.setName("Updated Dileep");
        request.setEmail("updated@gmail.com");
        request.setCourse("Advanced Spring Boot");
        request.setPhone("9876543210");

        Student existingStudent = new Student();
        existingStudent.setRollNo(1);
        existingStudent.setName("Old Name");
        existingStudent.setEmail("old@gmail.com");
        existingStudent.setCourse("Java");
        existingStudent.setPhone("9999999999");

        when(studentRepository.findById(1))
                .thenReturn(Optional.of(existingStudent));

        when(studentRepository.save(existingStudent))
                .thenReturn(existingStudent);

        StudentResponse response = studentService.updateStudent(1, request);

        assertNotNull(response);
        assertEquals("Updated Dileep", response.getName());
        assertEquals("updated@gmail.com", response.getEmail());
        assertEquals("Advanced Spring Boot", response.getCourse());
        assertEquals("9876543210", response.getPhone());

        verify(studentRepository).findById(1);
        verify(studentRepository).save(existingStudent);
    }
    @Test
    void testDeleteStudent() {

        String response = studentService.deleteStudent(1);

        assertEquals("Student Deleted Successfully", response);

        verify(studentRepository).deleteById(1);
    }
    @Test
    void testGetStudentByRollNo_NotFound() {

        when(studentRepository.findById(100))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class, () -> {
                    studentService.getStudentByRollNo(100);
                });

        assertEquals(
                "Student not found with Roll No : 100",
                exception.getMessage()
        );

        verify(studentRepository).findById(100);
    }
    @Test
    void testSaveStudent_DuplicateEmail() {

        StudentRequest request = new StudentRequest();
        request.setName("Dileep");
        request.setEmail("dileep@gmail.com");
        request.setCourse("Spring Boot");
        request.setPhone("9876543210");

        Student existingStudent = new Student();
        existingStudent.setEmail("dileep@gmail.com");

        when(studentRepository.findByEmail("dileep@gmail.com"))
                .thenReturn(Optional.of(existingStudent));

        DuplicateEmailException exception =
                assertThrows(DuplicateEmailException.class, () -> {
                    studentService.saveStudent(request);
                });

        assertEquals("Email already exists", exception.getMessage());

        verify(studentRepository).findByEmail("dileep@gmail.com");
    }
}