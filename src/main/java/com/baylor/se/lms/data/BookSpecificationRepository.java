package com.baylor.se.lms.data;

import com.baylor.se.lms.model.BookSpecification;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookSpecificationRepository extends PagingAndSortingRepository<BookSpecification,Long> {
    List<BookSpecification> findAllByNameContaining(String bookName);
}
