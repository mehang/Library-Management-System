package com.baylor.se.lms.data;

import com.baylor.se.lms.model.Author;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
}
