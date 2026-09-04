package com.dileep.studentmanagement.service;

import com.dileep.studentmanagement.entity.Student;
import com.dileep.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dileep.studentmanagement.exception.ResourceNotFoundException;
import java.util.List;
import com.dileep.studentmanagement.exception.DuplicateEmailException;
import com.dileep.studentmanagement.dto.StudentRequest;
import com.dileep.studentmanagement.dto.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentService {
    private static final Logger logger =
            LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    public Page<StudentResponse> getAllStudents(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Student> studentPage = studentRepository.findAll(pageable);

        return studentPage.map(this::convertToResponse);
    }


    public StudentResponse saveStudent(StudentRequest request) {
        logger.info("Received request to save student with email: {}", request.getEmail());

        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            logger.warn("Duplicate email detected: {}", request.getEmail());

            throw new DuplicateEmailException("Email already exists");
        }

        Student student = new Student();

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setCourse(request.getCourse());
        student.setPhone(request.getPhone());

        Student savedStudent = studentRepository.save(student);
        logger.info("Student saved successfully with Roll No: {}", savedStudent.getRollNo());

        return convertToResponse(savedStudent);
    }
    public StudentResponse getStudentByRollNo(Integer rollNo) {
        logger.info("Fetching student with Roll No: {}", rollNo);

        Student student = studentRepository.findById(rollNo)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with Roll No : " + rollNo));

        StudentResponse response = new StudentResponse();

        response.setRollNo(student.getRollNo());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setCourse(student.getCourse());
        response.setPhone(student.getPhone());

        return response;
    }

    public StudentResponse updateStudent(Integer rollNo, StudentRequest request) {
        logger.info("Updating student with Roll No: {}", rollNo);

        Student existingStudent = studentRepository.findById(rollNo)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with Roll No : " + rollNo));

        existingStudent.setName(request.getName());
        existingStudent.setEmail(request.getEmail());
        existingStudent.setCourse(request.getCourse());
        existingStudent.setPhone(request.getPhone());

        Student updatedStudent = studentRepository.save(existingStudent);
        logger.info("Student updated successfully with Roll No: {}", updatedStudent.getRollNo());

        return convertToResponse(updatedStudent);
    }
    public String deleteStudent(Integer rollNo) {
        studentRepository.deleteById(rollNo);
        return "Student Deleted Successfully";
    }

    public List<StudentResponse> searchStudentsByName(String name) {

        List<Student> students = studentRepository.findByNameContainingIgnoreCase(name);

        return students.stream()
                .map(this::convertToResponse)
                .toList();
    }
        public StudentResponse getStudentByEmail(String email) {

            Student student = studentRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Student not found with Email : " + email));

            return convertToResponse(student);

    }
    public List<StudentResponse> searchStudentsByCourse(String course) {

        List<Student> students = studentRepository.findByCourseContainingIgnoreCase(course);

        return students.stream()
                .map(this::convertToResponse)
                .toList();
    }
    public List<StudentResponse> searchStudents(String name, String course) {

        List<Student> students =
                studentRepository.findByNameContainingIgnoreCaseAndCourseContainingIgnoreCase(name, course);

        return students.stream()
                .map(this::convertToResponse)
                .toList();
    }
    private StudentResponse convertToResponse(Student student) {

        StudentResponse response = new StudentResponse();

        response.setRollNo(student.getRollNo());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setCourse(student.getCourse());
        response.setPhone(student.getPhone());

        return response;
    }
}