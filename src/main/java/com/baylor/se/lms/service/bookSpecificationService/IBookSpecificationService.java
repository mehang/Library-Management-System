package com.baylor.se.lms.service.bookSpecificationService;

import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.model.BookSpecification;

import java.util.List;

public interface IBookSpecificationService {
    public BookSpecification saveBookSpec(BookSpecification bookSpecification);
    public BookSpecification getBookSpec(Long id);
    public List<BookSpecification> getBookspec();
    public void updateBookSpec(BookSpecification book);
}
