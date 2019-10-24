package com.baylor.se.lms.data;

import com.baylor.se.lms.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book,Long> {
}