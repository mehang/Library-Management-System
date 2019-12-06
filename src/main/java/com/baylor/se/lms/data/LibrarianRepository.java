package com.baylor.se.lms.data;

import com.baylor.se.lms.model.Librarian;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Librarian
 */
public interface LibrarianRepository extends CrudRepository<Librarian, Long> {
    @Query("select  L from Librarian L where L.id = ?1 and L.deleteFlag = false")
    Optional<Librarian> findLibrarianById(double id);

    List<Librarian> findAllByDeleteFlagFalse();
}

