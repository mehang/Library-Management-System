package com.baylor.se.lms.service;

import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookLoan;

import java.util.List;

public interface IBookLoanService {
   public List<BookLoan> getBookLoanByUser(String username);
   public List<BookLoan> getBookLoanByBook(long bookId);
   public BookLoan getBookLoan(Long id);

}
