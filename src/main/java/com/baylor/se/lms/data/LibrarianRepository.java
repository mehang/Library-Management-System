package com.baylor.se.lms.data;

import com.baylor.se.lms.model.Librarian;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LibrarianRepository extends UserBaseRepository<Librarian>, CrudRepository<Librarian, Long> {
}

