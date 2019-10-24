package com.baylor.se.lms.service.bookService;

import com.baylor.se.lms.Book;

public interface IBookService {
    public Book registerBook(Book book);
    public Book getBook(Long id);
    public void updateBook(Book book);
}
