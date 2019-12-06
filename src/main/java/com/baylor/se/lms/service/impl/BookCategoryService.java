package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.BookCategoryRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.BookCategory;
import com.baylor.se.lms.service.IBookCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *  Handles all CRUD operations for Book Category. Implements IBookCategoryService interface
 */
@Service
@Slf4j
public class BookCategoryService implements IBookCategoryService {
    @Autowired
    BookCategoryRepository bookCategoryRepository;

    @Autowired
    JmsTemplate jmsTemplate;

    /**
     * Add new book category
     * @param category : Category information
     * @return BookCategory :  saved book category
     */
    @Override
    public BookCategory registerBookCategory(BookCategory category) {
        log.info("Saving Category " + category.getName());
        return bookCategoryRepository.save(category);
    }

    /**
     *  Get non-deleted bookCategory of provide id d
     * @param id:  Book Category Id
     * @return BookCategory:  Non-deleted Book Category
     */
    @Override
    public BookCategory getBookCategory(Long id) {
        log.info("Retrieving Category with id " + id);
        return bookCategoryRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * Get All Book Category
     * @return List of BookCategory : All bookCategory with delete flag false
     */
    @Override
    public List<BookCategory> getBookCategories() {

        log.info("Retrieving all categories");
        List<BookCategory> categories = bookCategoryRepository.findAllByDeleteFlagFalse();
        return categories;
    }

    /**
     *  Update Book Category with given id
     * @param category : Update Detail of category
     * @return BookCategory: Updated BookCategory
     */
    @Override
    public BookCategory updateBookCategory(BookCategory category) {


        log.info("Updating category: " +  category.getName());
        return bookCategoryRepository.save(category);
    }

    /**
     * Soft Delete book category  of provided id.
     * @param id: CategoryId
     */
    @Override
    public void deleteBookCategory(Long id){
        log.info("Deleting category with id" + id);
        BookCategory category = getBookCategory(id);
        category.setDeleteFlag(true);
        updateBookCategory(category);
        jmsTemplate.convertAndSend("post-category-delete", category);
    }

    /**
     * Appends timestamp to current category name and updates the database. It preservers the uniqueness of category name
     * @param category : deleted category
     */
    @JmsListener(destination = "post-category-delete", containerFactory = "postDeleteFactory")
    public void postDelete(BookCategory category) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        category.setName(category.getName()+timeStamp);
        bookCategoryRepository.save(category);
    }
}
