package com.baylor.se.lms.unit;

import com.baylor.se.lms.data.BookRepository;
import com.baylor.se.lms.dto.BookDTO;
import com.baylor.se.lms.dto.BookIssueDTO;
import com.baylor.se.lms.dto.BookRequestDTO;
import com.baylor.se.lms.dto.BookReturnDTO;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.*;
import com.baylor.se.lms.service.impl.BookService;
import com.baylor.se.lms.service.impl.LibrarianService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
public class BookTest {
//    @Autowired
//    BookService bookService;
//
//    @Autowired
//    BookRepository bookRepository;
//
//    @Autowired
//    LibrarianService librarianService;

//    @Test
//    public void testBookCreation() {
//        BookDTO bookDTO = new BookDTO();
//        bookDTO.setAuthorId(1);
//        bookDTO.setLibrarianId(4L);
//        bookDTO.setEdition("3rd");
//        bookDTO.setIsbn("123456789");
//        bookDTO.setLanguage("English");
//        bookDTO.setPublication("1st");
//        bookDTO.setName("Test Book");
//        Book book = bookService.registerBook(bookDTO);
//        Assert.assertEquals(book.getSpecification().getName(), "Test Book");
//    }
//
//    @Test
//    public void testBookIncrement() {
//        String isbn = "121";
//        long id = 4L;
//        int bookCounter = bookRepository.findBooksBySerialNoContaining("121").size();
//        bookService.increaseBook(isbn, id);
//        int newBookCounter = bookRepository.findBooksBySerialNoContaining(isbn).size();
//        Assert.assertEquals(bookCounter + 1, newBookCounter);
//    }
//
//    @Test
//    public void testUpdateBook() {
//        BookDTO bookDTO = new BookDTO();
//        bookDTO.setBookId(2L);
//        bookDTO.setAuthorId(1);
//        bookDTO.setLibrarianId(4L);
//        bookDTO.setEdition("3rd");
//        bookDTO.setIsbn("123456789");
//        bookDTO.setLanguage("English");
//        bookDTO.setPublication("1st");
//        bookDTO.setName("Test Book 123");
//        Set<Long> catSet = new HashSet<>();
//        catSet.add(1L);
//        bookDTO.setBookCategory(catSet);
//        BookSpecification bookSpecification = bookService.updateBook(bookDTO);
//        Assert.assertEquals("Test Book 123", bookSpecification.getName());
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void testUpdateBookWithInvalidBookId() {
//        BookDTO bookDTO = new BookDTO();
//        bookDTO.setBookId(12L); // Invalid Id
//        bookService.updateBook(bookDTO);
//    }
//
//    @Test
//    public void testRequestBook() {
//        BookRequestDTO bookRequestDTO = new BookRequestDTO();
//        bookRequestDTO.setBookId(2L);
//        bookRequestDTO.setUserId(6L);
//        BookLoan bookLoan = bookService.requestForBook(bookRequestDTO);
//        Book book = bookService.getBook(2L);
//        //Assert Book Status is changed
//        Assert.assertEquals(Book.BookStatus.NOT_AVAILABLE, book.getStatus());
//        //Assert Book Log is created
//        Assert.assertEquals(bookLoan.getLog().size(),1);
//        Set<BookLog> bookLogSet = bookLoan.getLog();
//        // Assert Book Log is set for REQUESTED
//        bookLogSet.forEach(s -> Assert.assertEquals(s.getAction(),BookLog.Action.REQUEST));
//    }
//
//    @Test
//    public void testIssuedBook() {
//        BookRequestDTO bookRequestDTO = new BookRequestDTO();
//        bookRequestDTO.setBookId(17L);
//        bookRequestDTO.setUserId(6L);
//        bookService.requestForBook(bookRequestDTO);
//
//        BookIssueDTO bookIssueDTO = new BookIssueDTO();
//        bookIssueDTO.setBookId(17L);
//        bookIssueDTO.setUserId(4L);
//        BookLoan bookLoan = bookService.issueBook(bookIssueDTO);
//        Assert.assertEquals(bookLoan.getStatus(), BookLoan.LoanStatus.ISSUED);
//        //Assert Book Log is created
//        Assert.assertEquals(bookLoan.getLog().size(),2);
//
//
//
//    }
//
//    @Test
//    public void testReturnBook() {
//        BookRequestDTO bookRequestDTO = new BookRequestDTO();
//        bookRequestDTO.setBookId(17L);
//        bookRequestDTO.setUserId(6L);
//        bookService.requestForBook(bookRequestDTO);
//
//        BookIssueDTO bookIssueDTO = new BookIssueDTO();
//        bookIssueDTO.setBookId(17L);
//        bookIssueDTO.setUserId(4L);
//        bookService.issueBook(bookIssueDTO);
//
//        BookReturnDTO bookReturnDTO = new BookReturnDTO();
//        bookReturnDTO.setSerialNo("1120032Book1");
//        BookLoan bookLoan = bookService.returnBook(bookReturnDTO);
//        Assert.assertEquals(bookLoan.getStatus(), BookLoan.LoanStatus.RETURNED);
//        Assert.assertEquals(bookLoan.getLog().size(),3);
//
//        bookLoan.setLog(bookLoan.getLog().stream().filter(s -> s.getAction() == BookLog.Action.RETURN).collect(Collectors.toSet()));
//        // Assert Book Log is set for REQUESTED
//        Assert.assertEquals(bookLoan.getLog().size(),1);
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void testInvalidBookRequest() {
//        BookRequestDTO bookRequestDTO = new BookRequestDTO();
//        bookRequestDTO.setBookId(22L);
//        bookRequestDTO.setUserId(6L);
//        bookService.requestForBook(bookRequestDTO);
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void testInvalidBookIssued() {
//        BookIssueDTO bookIssueDTO = new BookIssueDTO();
//        bookIssueDTO.setBookId(22L);
//        bookIssueDTO.setUserId(4L);
//        bookService.issueBook(bookIssueDTO);
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void testInvalidBookReturned() {
//        BookReturnDTO bookReturnDTO = new BookReturnDTO();
//        bookReturnDTO.setSerialNo("1120032Book123");
//        bookService.returnBook(bookReturnDTO);
//    }
}
