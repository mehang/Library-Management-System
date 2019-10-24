package com.baylor.se.lms.controller;

import com.baylor.se.lms.Book;
import com.baylor.se.lms.service.bookService.impl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URI;
import java.util.List;

@RestController
public class BookController {
    Book book = new Book();

    @Autowired
    BookService bookService;

    @GetMapping(path="/books", produces="application/json")
    public ResponseEntity<List<Book>> getBooks(){
        List<Book> books =  bookService.getBooks();
        return ResponseEntity.ok().body(books);
    }

    @GetMapping(path="/books/{id:[0-9][0-9]*}", produces="application/json")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @PostMapping(path="/books/",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book registeredBook = bookService.registerBook(book);
        return ResponseEntity.ok().body(registeredBook);
    }
}
