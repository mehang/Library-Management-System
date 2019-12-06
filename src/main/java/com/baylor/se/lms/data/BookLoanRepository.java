package com.baylor.se.lms.data;


import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookLoan;
import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for Book Loan
 */

public interface BookLoanRepository extends CrudRepository<BookLoan, Long> {

    BookLoan findByBookAndStatus(Book book, BookLoan.LoanStatus status);
    List<BookLoan> findAllByBook(Book book);
    List<BookLoan> findAllByRequestedBy(User user);
    List<BookLoan> findAllByRequestedByAndActualDateOfReturnIsNull(User user);
    List<BookLoan> findAllByRequestedByAndStatus(User user, BookLoan.LoanStatus status);

}
