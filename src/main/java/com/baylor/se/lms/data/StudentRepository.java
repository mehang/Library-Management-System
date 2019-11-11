package com.baylor.se.lms.data;

import com.baylor.se.lms.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends UserBaseRepository<Student>, CrudRepository<Student, Long> {
}
