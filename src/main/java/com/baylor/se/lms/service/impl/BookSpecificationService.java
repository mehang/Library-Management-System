package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.BookRepository;
import com.baylor.se.lms.data.BookSpecificationRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookSpecification;
import com.baylor.se.lms.service.IBookSpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles all CRUD operation for book specification. Additionally, can search book by name.
 * Implements IBookSepecificationService interface
 */
@Service
@Slf4j
public class BookSpecificationService implements IBookSpecificationService {
    @Autowired
    BookSpecificationRepository bookSpecificationRepository;

    @Autowired
    BookRepository bookRepo;
    @Override
    public BookSpecification saveBookSpec(BookSpecification bookSpecification) {
        log.info("Saving book specification: "+ bookSpecification.getName() );
        return bookSpecificationRepository.save(bookSpecification);
    }

    @Override
    public BookSpecification getBookSpec(Long id) {
        log.info("Get book specification by id: "+ id);
        return bookSpecificationRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<BookSpecification> getBookspec() {
        log.info("Get all book specifications");
        List <BookSpecification> bookSpecs = (List<BookSpecification>)bookSpecificationRepository.findAll();
        bookSpecs.removeIf(BookSpecification::isDeleteFlag);
        return bookSpecs;
    }

    @Override
    public BookSpecification updateBookSpec(BookSpecification book)  {

        return bookSpecificationRepository.save(book);
    }

    @Override
    public BookSpecification deleteBookSpec(Long id) {
        log.info("Deleting Book Specification: " + id);
        BookSpecification bookSpecification = bookSpecificationRepository.findById(id).orElseThrow(NotFoundException::new);
        bookSpecification.setDeleteFlag(true);
        bookSpecificationRepository.save(bookSpecification);
        List<Book> bookList = bookRepo.findAllBySpecificationAndDeleteFlagFalse(bookSpecification);
        log.info("Deleting all book  related to book Specification");
        for(Book book: bookList){
            book.setDeleteFlag(true);
            bookRepo.save(book);

        }
        log.info("BookSpecification deleted" + bookSpecification.getName());
        return bookSpecification;
    }

    public List<BookSpecification> searchByBookName(String bookName){
        log.info("Search Book by name: "+ bookName);
        return bookSpecificationRepository.findAllByNameContaining(bookName);
    }
}
