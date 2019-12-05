package com.baylor.se.lms.controller;


import com.baylor.se.lms.dto.*;
import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookCategory;
import com.baylor.se.lms.model.BookLoan;
import com.baylor.se.lms.service.impl.BookCategoryService;
import com.baylor.se.lms.service.impl.BookLoanService;
import com.baylor.se.lms.service.impl.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    BookLoanService bookLoanService;

    @Autowired
    BookCategoryService bookCategoryService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping(path="/books", produces="application/json")
    public ResponseEntity<List<Book>> getBooks(){

        List<Book> bookList =  bookService.getBooks();
        log.info("All book fetched");
        return ResponseEntity.ok().body(bookList);
    }

    @GetMapping(path="/books/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        log.info("Book Fetched " +  book.getSpecification().getName());
        return ResponseEntity.ok().body(book);
    }

    @PostMapping(path="/books/",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<Book> addBook(@RequestBody BookDTO book) {
        Book registeredBook = bookService.registerBook(book);
        log.info("Book added: " +  book.getName());
        return ResponseEntity.ok().body(registeredBook);
    }

    @PutMapping(path="/books/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Book> updateBook(@RequestBody BookDTO bookDTO) {
        log.info("Updating Book");

        Book updatedBook = bookService.updateBook(bookDTO);
        log.info("Book updated: "+ updatedBook.getSpecification().getName());
        return ResponseEntity.ok().body(updatedBook);
    }

    @PostMapping(path = "/books/increase",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<Book> increaseBook(@RequestBody BookAddDTO bookAddDTO){
        Book book = bookService.increaseBook(bookAddDTO.getIsbn(),bookAddDTO.getUserId());
        log.info("Book Increased " + book.getSpecification().getName());
        return  ResponseEntity.ok().body(book);
    }

    @PostMapping(path = "/books/request",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookLoan> requestBook(@RequestBody BookRequestDTO bookRequestDTO){

        BookLoan bookLoan = bookService.requestForBook(bookRequestDTO);
        log.info("Book Requested Successful "  + bookRequestDTO.getBookId());
        simpMessagingTemplate.convertAndSend("/lms/requested", bookRequestDTO.getBookId());
        return  ResponseEntity.ok().body(bookLoan);
    }

    @GetMapping(path = "books/search",produces = "application/json")
    @ResponseBody
//    @MessageMapping("/books/search")
//    @SendTo("/lms/requested")
    public ResponseEntity<List<SearchDTO>> searchBooks(@RequestParam(required = true) String q){
        List<SearchDTO> bookList = bookService.searchBooks(q);
        log.info("Book Searched  for query: " + q);
        return ResponseEntity.ok().body(bookList);
    }

    @PostMapping(path = "books/issue",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookLoan> issueBook(@RequestBody BookIssueDTO bookIssueDTO){
        BookLoan bookLoan =  bookService.issueBook(bookIssueDTO);
        log.info("Book issued Successful "  + bookIssueDTO.getBookId());
        return ResponseEntity.ok().body(bookLoan);
    }

    @PostMapping(path = "books/return",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookLoan> returnBook(@RequestBody BookReturnDTO bookReturnDTO){
        BookLoan bookLoan =  bookService.returnBook(bookReturnDTO);
        log.info("Book Loan successful" + bookReturnDTO.getSerialNo());
        return ResponseEntity.ok().body(bookLoan);
    }

    @GetMapping(path = "books/{id:[0-9][0-9]*}/bookloans",produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<BookLoan>> searchBooks(@PathVariable Long id){
        List<BookLoan> bookList = bookLoanService.getBookLoanByBook(id);
        log.info("Fetched all the bookLoan service");
        return ResponseEntity.ok().body(bookList);
    }

    @GetMapping(path = "/books/categories", produces="application/json")
    public ResponseEntity getCategories(){
        List<BookCategory> categories = bookCategoryService.getBookCategories();
        log.info("Fetched book categories : " + categories.size());
        return ResponseEntity.ok().body(categories);
    }
    @GetMapping(path="/books/categories/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity getCategory(@PathVariable Long id) {
        log.info("Categories fetched: " + id);
        BookCategory category = bookCategoryService.getBookCategory(id);
        return ResponseEntity.ok().body(category);
    }

    @PostMapping(path="/books/categories",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity addCategory(@RequestBody BookCategory bookCategory) {
        BookCategory registeredCategory = bookCategoryService.registerBookCategory(bookCategory);
        log.info("Category added " + registeredCategory.getName());
        return ResponseEntity.ok().body(registeredCategory);
    }

    @PutMapping(path="/books/categories/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateCategory(@RequestBody BookCategory bookCategory, @PathVariable Long id) {
        BookCategory updatedBookCategory = bookCategoryService.updateBookCategory(bookCategory);
        log.info("Category updated " + updatedBookCategory.getName());
        return ResponseEntity.ok().body(updatedBookCategory);
    }

    @DeleteMapping(path="/books/categories/{id:[0-9][0-9]*}")
    public ResponseEntity deleteCategory(@PathVariable Long id){
        bookCategoryService.deleteBookCategory(id);
        log.info("Category deleted " + id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
