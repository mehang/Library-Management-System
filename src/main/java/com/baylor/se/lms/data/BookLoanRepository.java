package com.baylor.se.lms.data;


import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookLoan;
import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookLoanRepository extends PagingAndSortingRepository<BookLoan, Long> {

    BookLoan findByBookAndStatus(Book book, BookLoan.LoanStatus status);
    BookLoan findByBookAndIssuedBy(Book book, Librarian librarian);
    List<BookLoan> findAllByBook(Book book);
    List<BookLoan> findAllByRequestedBy(User user);
}
