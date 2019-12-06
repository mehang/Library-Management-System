package com.baylor.se.lms.service;

import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookLoan;

import java.util.List;

public interface IBookLoanService {
   List<BookLoan> getBookLoanByUser(String username);
   List<BookLoan> getBookLoanByBook(long bookId);
   BookLoan getBookLoan(Long id);

}
