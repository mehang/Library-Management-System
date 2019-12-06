package com.baylor.se.lms.controller;


import com.baylor.se.lms.dto.*;
import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookCategory;
import com.baylor.se.lms.model.BookLoan;
import com.baylor.se.lms.model.BookSpecification;
import com.baylor.se.lms.service.impl.BookCategoryService;
import com.baylor.se.lms.service.impl.BookLoanService;
import com.baylor.se.lms.service.impl.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Book Controller handles all request concern with BOOK entity. All CRUD operation along with book request, issues and
 * return is handled by it. It uses various service like BookService, BookLoanService and BookCategoryService.
 */
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

    /**
     * Handles GET request to fetch all the books. Produces JSON  response with all books
     * @return Response with list of book
     */
    @GetMapping(path="/books", produces="application/json")
    public ResponseEntity<List<Book>> getBooks(){

        List<Book> bookList =  bookService.getBooks();
        log.info("All book fetched");
        return ResponseEntity.ok().body(bookList);
    }

    /**
     * Handle GET request to fetch single book of given id. The Id is passed as path variable
     * @param id :  Book Id  (Path variable)
     * @return JSON Response with Book records
     */
    @GetMapping(path="/books/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        log.info("Book Fetched " +  book.getSpecification().getName());
        return ResponseEntity.ok().body(book);
    }

    /**
     * Handles POST request to create new book. Consumes JSON with Book records.
     * @param book : Book information with specification in it.
     * @return JSON response with created Book record
     */
    @PostMapping(path="/books/",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<Book> addBook(@RequestBody BookDTO book) {
        Book registeredBook = bookService.registerBook(book);
        log.info("Book added: " +  book.getName());
        return ResponseEntity.ok().body(registeredBook);
    }

    /**
     * Handles PUT request to update Book (Book specification).
     * @param bookDTO : Book details to be updated
     * @return Updated Book Details as JSON response
     */
    @PutMapping(path="/books/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookSpecification> updateBook(@RequestBody BookDTO bookDTO) {
        log.info("Updating Book");

        BookSpecification updatedBook = bookService.updateBook(bookDTO);
        log.info("Book updated: "+ updatedBook.getName());
        return ResponseEntity.ok().body(updatedBook);
    }

    /**
     * Handles request to increase book by 1 for given book specification
     * @param bookAddDTO : Contains book ISBN
     * @return  JSON response with new book
     */
    @PostMapping(path = "/books/increase",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<Book> increaseBook(@RequestBody BookAddDTO bookAddDTO){
        Book book = bookService.increaseBook(bookAddDTO.getIsbn(),bookAddDTO.getUserId());
        log.info("Book Increased " + book.getSpecification().getName());
        return  ResponseEntity.ok().body(book);
    }

    /**
     * Handles  POST request for Book request. It consumes book request data.
     * @param bookRequestDTO : contains book and student id
     * @return Book Loan record as JSON response
     */
    @PostMapping(path = "/books/request",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookLoan> requestBook(@RequestBody BookRequestDTO bookRequestDTO){

        BookLoan bookLoan = bookService.requestForBook(bookRequestDTO);
        log.info("Book Requested Successful "  + bookRequestDTO.getBookId());
        simpMessagingTemplate.convertAndSend("/lms/requested", bookRequestDTO.getBookId());
        return  ResponseEntity.ok().body(bookLoan);
    }

    /**
     *  Handles GET request to search for books. It  gets query values from the URL.
     * @param q  : Query to be searched
     * @return List of books specification as JSON response
     */
    @GetMapping(path = "books/search",produces = "application/json")
    @ResponseBody
//    @MessageMapping("/books/search")
//    @SendTo("/lms/requested")
    public ResponseEntity<List<SearchDTO>> searchBooks(@RequestParam(required = true) String q){
        List<SearchDTO> bookList = bookService.searchBooks(q);
        log.info("Book Searched  for query: " + q);
        return ResponseEntity.ok().body(bookList);
    }

    /**
     * Handles Book Issues by user.
     * @param bookIssueDTO : contains bookId, and librarianId
     * @return JSON response with new Book Loan object
     */
    @PostMapping(path = "books/issue",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookLoan> issueBook(@RequestBody BookIssueDTO bookIssueDTO){
        BookLoan bookLoan =  bookService.issueBook(bookIssueDTO);
        log.info("Book issued Successful "  + bookIssueDTO.getBookId());
        return ResponseEntity.ok().body(bookLoan);
    }

    /**
     * Handles Book Return request by the user. Gets returned  book id.
     * @param bookReturnDTO :  Contains bookId
     * @return BookLoan record as JSON response
     */
    @PostMapping(path = "books/return",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookLoan> returnBook(@RequestBody BookReturnDTO bookReturnDTO){
        BookLoan bookLoan =  bookService.returnBook(bookReturnDTO);
        log.info("Book Loan successful" + bookReturnDTO.getSerialNo());
        return ResponseEntity.ok().body(bookLoan);
    }

    /**
     * Handles GET request for finding all book loans by book.
     * @param id :  book id (Path Variable)
     * @return List of BookLoans as JSON response
     */
    @GetMapping(path = "books/{id:[0-9][0-9]*}/bookloans",produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<BookLoan>> getBookLoansByBook(@PathVariable Long id){
        List<BookLoan> bookList = bookLoanService.getBookLoanByBook(id);
        log.info("Fetched all the bookLoan service");
        return ResponseEntity.ok().body(bookList);
    }

    /**
     * Handle GET  request to fetch all categories.
     * @return All categories
     */
    @GetMapping(path = "/books/categories", produces="application/json")
    public ResponseEntity getCategories(){
        List<BookCategory> categories = bookCategoryService.getBookCategories();
        log.info("Fetched book categories : " + categories.size());
        return ResponseEntity.ok().body(categories);
    }

    /**
     * Handles GET request to fetch category by id.
     * @param id : category id
     * @return Category as JSON response
     */
    @GetMapping(path="/books/categories/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity getCategory(@PathVariable Long id) {
        log.info("Categories fetched: " + id);
        BookCategory category = bookCategoryService.getBookCategory(id);
        return ResponseEntity.ok().body(category);
    }

    /**
     *  Creates Book category. Consumes JSON structure for category details
     * @param bookCategory : Book Category to be saved
     * @return saved  book category as JSON response
     */

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

    /**
     * Deletes category for given id.
     * @param id : Category Id
     * @return Response with HTTP status OK
     */
    @DeleteMapping(path="/books/categories/{id:[0-9][0-9]*}")
    public ResponseEntity deleteCategory(@PathVariable Long id){
        bookCategoryService.deleteBookCategory(id);
        log.info("Category deleted " + id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
