package com.baylor.se.lms.data;

import com.baylor.se.lms.model.BookCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Book Category entity
 */

public interface BookCategoryRepository extends CrudRepository<BookCategory,Long> {
    @Query("select c from BookCategory c where c.id = ?1 and c.deleteFlag = false")
    Optional<BookCategory> findBookCategoryById(long id);
    List<BookCategory> findAllByDeleteFlagFalse();
}
