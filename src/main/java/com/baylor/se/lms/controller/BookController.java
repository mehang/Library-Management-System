package com.baylor.se.lms.controller;

import com.baylor.se.lms.Book;
import com.baylor.se.lms.service.bookService.impl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URI;

@RestController
@RequestMapping("books")
public class BookController {
    Book book = new Book();

    @Autowired
    BookService bookService;

    @GetMapping(path="/{id:[0-9][0-9]*}", produces="application/json")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @PostMapping(path="/",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book registeredBook = bookService.registerBook(book);
        return ResponseEntity.ok().body(registeredBook);
    }
}
