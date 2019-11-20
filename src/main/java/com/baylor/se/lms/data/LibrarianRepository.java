package com.baylor.se.lms.data;

import com.baylor.se.lms.model.Librarian;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface LibrarianRepository extends UserBaseRepository<Librarian>, CrudRepository<Librarian, Long> {
    @Query("select  L from Librarian L where L.id = ?1 and L.deleteFlag = false")
    Optional<Librarian> findLibrarianById(double id);

    List<Librarian> findAllByDeleteFlagFalse();
}

