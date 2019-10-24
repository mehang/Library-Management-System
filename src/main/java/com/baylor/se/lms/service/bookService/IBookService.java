package com.baylor.se.lms.service.bookService;

import com.baylor.se.lms.Book;

import java.util.List;

public interface IBookService {
    public Book registerBook(Book book);
    public Book getBook(Long id);
    public List<Book> getBooks();
    public void updateBook(Book book);
}
