package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.BookLoanRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.BookLoan;
import com.baylor.se.lms.service.IBookLoanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookLoanService implements IBookLoanService {

    @Autowired
    BookLoanRepository bookLoanRepository;
    @Override
    public BookLoan saveBookLoan(BookLoan bookLoan) {
        return bookLoanRepository.save(bookLoan);
    }

    @Override
    public BookLoan getBookLoan(Long id) {

        BookLoan bookLoan = bookLoanRepository.findById(id).orElseThrow(NotFoundException::new);
        return bookLoan;
    }

    @Override
    public List<BookLoan> getBooks() {
        List<BookLoan>  bookLoans = (List<BookLoan>)bookLoanRepository.findAll();
        bookLoans.removeIf(BookLoan::isDeleteFlag);
        return bookLoans;
    }

    @Override
    public void updateBook(BookLoan bookLoan) {
        bookLoanRepository.save(bookLoan);
    }
}
