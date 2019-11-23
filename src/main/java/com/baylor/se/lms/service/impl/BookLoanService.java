package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.BookLoanRepository;
import com.baylor.se.lms.data.BookRepository;
import com.baylor.se.lms.data.UserRepository;
import com.baylor.se.lms.exception.BadRequestException;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.BookLoan;
import com.baylor.se.lms.model.Student;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.service.IBookLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookLoanService implements IBookLoanService {

    @Autowired
    BookLoanRepository bookLoanRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public BookLoan getBookLoan(Long id) {

        BookLoan bookLoan = bookLoanRepository.findById(id).orElseThrow(NotFoundException::new);
        return bookLoan;
    }

    @Override
    public List<BookLoan> getBookLoanByUser(String username){
        User student =  userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new);
        if (!student.getDecriminatorValue().equals("student")){
            throw new BadRequestException();
        }
        return bookLoanRepository.findAllByRequestedBy(student);
    }

    @Override
    public List<BookLoan> getBookLoanByBook(long bookId) {
        return null;
    }
}
