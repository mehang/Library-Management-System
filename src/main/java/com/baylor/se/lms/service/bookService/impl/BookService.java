package com.baylor.se.lms.service.bookService.impl;

import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.data.BookRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.service.bookService.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public Book registerBook(Book book){
        return bookRepository.save(book);
    }

    @Override
    public Book getBook(Long id){
        Book book =  bookRepository.findById(id).orElseThrow(NotFoundException::new);
        return book;
    }

    @Override
    public List<Book> getBooks(){
        List<Book> books = (List<Book>) bookRepository.findAll();
        books.removeIf(Book::isDeleteFlag);
        return books;
    }

    @Override
    public void updateBook(Book book){
        bookRepository.save(book);
    }

}
