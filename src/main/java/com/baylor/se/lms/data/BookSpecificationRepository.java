package com.baylor.se.lms.data;

import com.baylor.se.lms.model.BookSpecification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Book Specification
 */
public interface BookSpecificationRepository extends CrudRepository<BookSpecification,Long> {
    @Query("select  B from BookSpecification B where B.name = ?1 and B.deleteFlag = false")
    List<BookSpecification> findAllByNameContaining(String bookName);
    @Query("select  B from BookSpecification B where B.id = ?1 and B.deleteFlag = false")
    Optional<BookSpecification> findById(Long id);
}
