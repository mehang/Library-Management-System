package com.baylor.se.lms.controller;


import com.baylor.se.lms.dto.*;
import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookCategory;
import com.baylor.se.lms.model.BookLoan;
import com.baylor.se.lms.service.impl.BookCategoryService;
import com.baylor.se.lms.service.impl.BookLoanService;
import com.baylor.se.lms.service.impl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {


    @Autowired
    BookService bookService;

    @Autowired
    BookLoanService bookLoanService;

    @Autowired
    BookCategoryService bookCategoryService;

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
        Book updatedBook = bookService.updateBook(book);
        return ResponseEntity.ok().body(updatedBook);
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

    @GetMapping(path = "books/search",produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = true) String q){
        List<Book> bookList = bookService.searchBooks(q);
        return ResponseEntity.ok().body(bookList);
    }

    @PostMapping(path = "books/issue",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookLoan> issueBook(@RequestBody BookIssueDTO bookIssueDTO){
        BookLoan bookLoan =  bookService.issueBook(bookIssueDTO);
        return ResponseEntity.ok().body(bookLoan);
    }
    @PostMapping(path = "books/return",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookLoan> returnBook(@RequestBody BookReturnDTO bookReturnDTO){
        BookLoan bookLoan =  bookService.returnBook(bookReturnDTO);
        return ResponseEntity.ok().body(bookLoan);
    }

    @GetMapping(path = "books/{id:[0-9][0-9]*}/bookloans",produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<BookLoan>> searchBooks(@PathVariable Long id){
        List<BookLoan> bookList = bookLoanService.getBookLoanByBook(id);
        return ResponseEntity.ok().body(bookList);
    }

    @GetMapping(path = "/books/categories", produces="application/json")
    public ResponseEntity getCategories(){
        List<BookCategory> categories = bookCategoryService.getBookCategories();
        return ResponseEntity.ok().body(categories);
    }
    @GetMapping(path="/books/categories/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity getCategory(@PathVariable Long id) {
        BookCategory category = bookCategoryService.getBookCategory(id);
        return ResponseEntity.ok().body(category);
    }

    @PostMapping(path="/books/categories",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity addCategory(@RequestBody BookCategory bookCategory) {
        BookCategory registeredCategory = bookCategoryService.registerBookCategory(bookCategory);
        return ResponseEntity.ok().body(registeredCategory);
    }

    @PutMapping(path="/authors/categories/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateCategory(@RequestBody BookCategory bookCategory, @PathVariable Long id) {
        BookCategory updatedBookCategory = bookCategoryService.updateBookCategory(bookCategory);
        return ResponseEntity.ok().body(updatedBookCategory);
    }

}
