package com.dileep.studentmanagement.controller;
import com.dileep.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.dileep.studentmanagement.dto.StudentResponse;
import com.dileep.studentmanagement.dto.StudentRequest;
import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.dileep.studentmanagement.dto.PageResponse;


@RestController
@RequestMapping("/students")
@SecurityRequirement(name = "bearerAuth")
public class StudentController {


    @Autowired
    private StudentService studentService;
    @GetMapping
    public PageResponse<StudentResponse> getAllStudents(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "rollNo") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return studentService.getAllStudents(page, size, sortBy, direction);
    }
    @PostMapping
    public StudentResponse saveStudent(@Valid @RequestBody StudentRequest request) {

        return studentService.saveStudent(request);
    }

    @GetMapping("/{rollNo}")
    public StudentResponse getStudentByRollNo(@PathVariable Integer rollNo) {
        return studentService.getStudentByRollNo(rollNo);
    }

    @PutMapping("/{rollNo}")
    public StudentResponse updateStudent(@PathVariable Integer rollNo,
                                         @Valid @RequestBody StudentRequest request) {

        return studentService.updateStudent(rollNo, request);
    }

    @DeleteMapping("/{rollNo}")
    public String deleteStudent(@PathVariable Integer rollNo) {

        return studentService.deleteStudent(rollNo);
    }

    @GetMapping("/email/{email}")
    public StudentResponse getStudentByEmail(@PathVariable String email) {
        return studentService.getStudentByEmail(email);
    }

    @GetMapping("/search/name")
    public List<StudentResponse> searchStudentsByName(
            @RequestParam String name) {

        return studentService.searchStudentsByName(name);
    }
    @GetMapping("/search/course")
    public List<StudentResponse> searchStudentsByCourse(
            @RequestParam String course) {

        return studentService.searchStudentsByCourse(course);
    }
    @GetMapping("/search")
    public List<StudentResponse> searchStudents(

            @RequestParam String name,

            @RequestParam String course) {

        return studentService.searchStudents(name, course);
    }

}