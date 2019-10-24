package com.baylor.se.lms.service.bookService.impl;

import com.baylor.se.lms.Book;
import com.baylor.se.lms.data.BookRepository;
import com.baylor.se.lms.service.bookService.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService implements IBookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public void registerBook(Book book){
        bookRepository.save(book);
    }

    @Override
    public Book getBook(Long id){
        return bookRepository.findById(id);
    }

    @Override
    public void updateBook(Book book){
        bookRepository.save(book);
    }

}
