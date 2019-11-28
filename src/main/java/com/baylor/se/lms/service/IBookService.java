package com.baylor.se.lms.service;

import com.baylor.se.lms.dto.*;
import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookLoan;

import java.util.List;

public interface IBookService {
     Book registerBook(BookDTO book);
     Book getBook(Long id);
     List<Book> getBooks();
     Book updateBook(Book book);
     Book increaseBook(String isbn, long librarianId);
     BookLoan requestForBook(BookRequestDTO bookRequestDTO);
     List<SearchDTO> searchBooks(String bookName);
     BookLoan issueBook(BookIssueDTO bookIssueDTO);
     BookLoan returnBook(BookReturnDTO bookReturnDTO);


}
