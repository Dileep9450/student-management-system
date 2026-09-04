

package com.dileep.studentmanagement.repository;

import com.dileep.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByEmail(String email);

    List<Student> findByNameContainingIgnoreCase(String name);
    List<Student> findByCourseContainingIgnoreCase(String course);
    List<Student> findByNameContainingIgnoreCaseAndCourseContainingIgnoreCase(
            String name,
            String course
    );

}
