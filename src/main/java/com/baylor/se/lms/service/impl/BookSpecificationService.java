package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.BookSpecificationRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.BookSpecification;
import com.baylor.se.lms.service.IBookSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSpecificationService implements IBookSpecificationService {
    @Autowired
    BookSpecificationRepository bookSpecificationRepository;

    @Override
    public BookSpecification saveBookSpec(BookSpecification bookSpecification) {

        return bookSpecificationRepository.save(bookSpecification);
    }

    @Override
    public BookSpecification getBookSpec(Long id) {
        return bookSpecificationRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<BookSpecification> getBookspec() {
        List <BookSpecification> bookSpecs = (List<BookSpecification>)bookSpecificationRepository.findAll();
        bookSpecs.removeIf(BookSpecification::isDeleteFlag);
        return bookSpecs;
    }

    @Override
    public void updateBookSpec(BookSpecification book) {
        bookSpecificationRepository.save(book);
    }


}
