package com.baylor.se.lms.service.bookService;

import com.baylor.se.lms.Book;

public interface IBookService {
    public void registerBook(Book book);
    public void getBook(Long id);
    public void updateBook(Book book);
}
