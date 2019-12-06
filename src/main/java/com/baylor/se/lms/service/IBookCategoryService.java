package com.baylor.se.lms.service;

import com.baylor.se.lms.model.BookCategory;

import java.util.List;

public interface IBookCategoryService {
    BookCategory registerBookCategory(BookCategory category);
    BookCategory getBookCategory(Long id);
    List<BookCategory> getBookCategories();
    BookCategory updateBookCategory(BookCategory category);
    void deleteBookCategory(Long id);
}
