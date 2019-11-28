package com.baylor.se.lms.data;

import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookSpecification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends PagingAndSortingRepository<Book,Long> {

    @Query("select  B from Book B where B.id = ?1 and B.deleteFlag = false")
    Optional<Book> findBookById(long id);



    List<Book> findBooksBySerialNoContaining(String isbn);
    List<Book> findBooksBySpecificationAndStatusAndDeleteFlagFalse(BookSpecification bookSpec, Book.BookStatus status);

}
