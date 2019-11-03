package com.baylor.se.lms.service;

import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookLoan;

import java.util.List;

public interface IBookLoanService {
    public BookLoan saveBookLoan(BookLoan bookLoan);
    public BookLoan getBookLoan(Long id);
    public List<BookLoan> getBooks();
    public void updateBook(BookLoan bookLoan);
}
