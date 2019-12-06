package com.baylor.se.lms.service;

import com.baylor.se.lms.model.BookSpecification;

import java.util.List;

/**
 * Interface for Book Specification Services
 */
public interface IBookSpecificationService {
    BookSpecification saveBookSpec(BookSpecification bookSpecification);
    BookSpecification getBookSpec(Long id);
    List<BookSpecification> getBookspec();
    BookSpecification updateBookSpec(BookSpecification book);
    BookSpecification deleteBookSpec(Long id);
}
