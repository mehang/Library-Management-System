package com.baylor.se.lms.service;

import com.baylor.se.lms.dto.BookDTO;
import com.baylor.se.lms.dto.BookRequestDTO;
import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookLoan;

import java.util.List;

public interface IBookService {
    public Book registerBook(BookDTO book);
    public Book getBook(Long id);
    public List<Book> getBooks();
    public void updateBook(Book book);
    public Book increaseBook(String isbn, long librarianId);
    public BookLoan requestForBook(BookRequestDTO bookRequestDTO);

}
