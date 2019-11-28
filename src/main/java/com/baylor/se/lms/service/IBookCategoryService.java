package com.baylor.se.lms.service;

import com.baylor.se.lms.model.BookCategory;

import java.util.List;

public interface IBookCategoryService {
    public BookCategory registerBookCategory(BookCategory category);
    public BookCategory getBookCategory(Long id);
    public List<BookCategory> getBookCategories();
    public BookCategory updateBookCategory(BookCategory category);
    public void deleteBookCategory(Long id);
}
