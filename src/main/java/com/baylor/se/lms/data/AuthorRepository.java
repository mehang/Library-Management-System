package com.baylor.se.lms.data;

import com.baylor.se.lms.model.Admin;
import com.baylor.se.lms.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    @Query("select  A from Author A where A.id = ?1 and A.deleteFlag = false")
    Optional<Author> findAuthorById(long id);

     List<Author> findAllByDeleteFlagFalse();

}
