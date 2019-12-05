package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.BookLoanRepository;
import com.baylor.se.lms.data.BookRepository;
import com.baylor.se.lms.data.UserRepository;
import com.baylor.se.lms.exception.BadRequestException;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookLoan;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.service.IBookLoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class BookLoanService implements IBookLoanService {

    @Autowired
    BookLoanRepository bookLoanRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepo;

    /**
     *  Returns Book Loan record of given id  whose delete flag is set false. If not found, throws not found exception.
     * @param id : Book Loan id
     * @return BookLoan : fetched book loan record from database
     */
    @Override
    public BookLoan getBookLoan(Long id) {
        log.info("Getting  Bookloan  id :  " + id);
        BookLoan bookLoan = bookLoanRepository.findById(id).orElseThrow(NotFoundException::new);
        return bookLoan;
    }

    /**
     * Returns all book loan records of given user. If user is not found or user is not a student, throw exception
     * @param username : student username
     * @return List<BookLoan> : List of all bookloan records
     */
    @Override
    public List<BookLoan> getBookLoanByUser(String username){
        log.info("Get  All BookLoan by Username: ");
        User student =  userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new);
        log.info("Getting  Bookloan  of username:  " + student.getUsername());
        if (!student.getDiscriminatorValue().equalsIgnoreCase("student")){
            throw new BadRequestException();
        }
        return bookLoanRepository.findAllByRequestedBy(student);
    }

    /**
     * Returns all book loan record of given book. Throws exception if book is not found.
     * @param bookId : Book id
     * @return List <BookLoan>: All book loan records for the given book id.
     */
    @Override
    public List<BookLoan> getBookLoanByBook(long bookId) {
        log.info("Get BookLoan by book");
        Book book = bookRepo.findBookById(bookId).orElseThrow(NotFoundException::new);
        log.info("Book loan records of : "+ book.getSpecification().getName());
        List<BookLoan> bookLoans = bookLoanRepository.findAllByBook(book);

        return bookLoans;
    }
}
