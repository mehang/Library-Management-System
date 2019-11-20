package com.baylor.se.lms.service;

import com.baylor.se.lms.dto.BookDTO;
import com.baylor.se.lms.model.Book;

import java.util.List;

public interface IBookService {
    public Book registerBook(BookDTO book);
    public Book getBook(Long id);
    public List<Book> getBooks();
    public void updateBook(Book book);
}
