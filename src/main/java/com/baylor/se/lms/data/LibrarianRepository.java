package com.baylor.se.lms.data;

import com.baylor.se.lms.Librarian;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LibrarianRepository extends PagingAndSortingRepository<Librarian,Long> {
}
