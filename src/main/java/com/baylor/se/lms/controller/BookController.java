package com.baylor.se.lms.controller;


import com.baylor.se.lms.dto.BookAddDTO;
import com.baylor.se.lms.dto.BookDTO;
import com.baylor.se.lms.dto.BookRequestDTO;
import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookLoan;
import com.baylor.se.lms.service.impl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {


    @Autowired
    BookService bookService;

    @GetMapping(path="/books", produces="application/json")
    public ResponseEntity<List<Book>> getBooks(){
        List<Book> bookList =  bookService.getBooks();
        return ResponseEntity.ok().body(bookList);
    }

    @GetMapping(path="/books/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping(path="/books/",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<Book> addBook(@RequestBody BookDTO book) {
        Book registeredBook = bookService.registerBook(book);
        return ResponseEntity.ok().body(registeredBook);
    }

    @PutMapping(path="/books/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping(path = "/books/increase",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<Book> increaseBook(@RequestBody BookAddDTO bookAddDTO){
        Book book = bookService.increaseBook(bookAddDTO.getIsbn(),bookAddDTO.getUserId());
        return  ResponseEntity.ok().body(book);
    }

    @PostMapping(path = "/books/request",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookLoan> requestBook(@RequestBody BookRequestDTO bookRequestDTO){
        BookLoan bookLoan = bookService.requestForBook(bookRequestDTO);
        return  ResponseEntity.ok().body(bookLoan);
    }

    @GetMapping(path = "book/search",produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = true) String q){
        List<Book> bookList = bookService.searchBooks(q);
        return ResponseEntity.ok().body(bookList);
    }



}
