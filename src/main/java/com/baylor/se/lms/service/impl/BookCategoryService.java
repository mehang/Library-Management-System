package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.BookCategoryRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.BookCategory;
import com.baylor.se.lms.service.IBookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCategoryService implements IBookCategoryService {
    @Autowired
    BookCategoryRepository bookCategoryRepository;

    @Override
    public BookCategory registerBookCategory(BookCategory category) {
        return bookCategoryRepository.save(category);
    }

    @Override
    public BookCategory getBookCategory(Long id) {
        return bookCategoryRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<BookCategory> getBookCategories() {
        List<BookCategory> categories = bookCategoryRepository.findAllByDeleteFlagFalse();
        return categories;
    }

    @Override
    public BookCategory updateBookCategory(BookCategory category) {
        return bookCategoryRepository.save(category);
    }

    @Override
    public void deleteBookCategory(Long id){
        BookCategory category = getBookCategory(id);
        category.setDeleteFlag(true);
        updateBookCategory(category);
    }
}
