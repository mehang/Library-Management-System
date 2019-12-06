package com.baylor.se.lms.data;

import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookSpecification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Book Entity. Contains few custom query
 */

public interface BookRepository extends CrudRepository<Book,Long> {

    @Query("select  B from Book B where B.id = ?1 and B.deleteFlag = false")
    Optional<Book> findBookById(long id);

    @Query("select  B from Book B where B.serialNo = ?1 and B.deleteFlag = false")
    Optional<Book> findBookBySerialNo(String serialNo);

    List<Book> findBooksBySerialNoContaining(String isbn);
    List<Book> findBooksBySpecificationAndStatusAndDeleteFlagFalse(BookSpecification bookSpec, Book.BookStatus status);

    List<Book> findAllBySpecificationAndDeleteFlagFalse(BookSpecification bookSpecification);
}
