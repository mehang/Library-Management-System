package com.baylor.se.lms.data;

import com.baylor.se.lms.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
}
