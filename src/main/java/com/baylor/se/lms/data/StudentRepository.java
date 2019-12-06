package com.baylor.se.lms.data;

import com.baylor.se.lms.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for student entity
 */
public interface StudentRepository extends CrudRepository<Student, Long> {
    List<Student> findAllByDeleteFlagFalse() ;
}
