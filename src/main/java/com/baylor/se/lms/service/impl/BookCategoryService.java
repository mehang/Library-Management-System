package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.BookCategoryRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.BookCategory;
import com.baylor.se.lms.service.IBookCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookCategoryService implements IBookCategoryService {
    @Autowired
    BookCategoryRepository bookCategoryRepository;

    @Override
    public BookCategory registerBookCategory(BookCategory category) {
        log.info("Saving Category " + category.getName());
        return bookCategoryRepository.save(category);
    }

    @Override
    public BookCategory getBookCategory(Long id) {
        log.info("Retrieving Category with id " + id);
        return bookCategoryRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<BookCategory> getBookCategories() {

        log.info("Retrieving all categories");
        List<BookCategory> categories = bookCategoryRepository.findAllByDeleteFlagFalse();
        return categories;
    }

    @Override
    public BookCategory updateBookCategory(BookCategory category) {


        log.info("Updating category: " +  category.getName());
        return bookCategoryRepository.save(category);
    }

    @Override
    public void deleteBookCategory(Long id){
        log.info("Deleting category with id" + id);
        BookCategory category = getBookCategory(id);
        category.setDeleteFlag(true);
        updateBookCategory(category);
    }
}
