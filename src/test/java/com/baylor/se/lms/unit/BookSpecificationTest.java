package com.baylor.se.lms.unit;

import com.baylor.se.lms.data.BookSpecificationRepository;
import com.baylor.se.lms.model.BookSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.fail;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BookSpecificationTest {
    @Autowired
    BookSpecificationRepository bookSpecificationRepository;


    @Test(expected = ConstraintViolationException.class)
    public void testBookNameNull() {
        BookSpecification bookSpecification =  new BookSpecification();
        bookSpecification.setName(null);
        bookSpecification.setIsbn("1234123");
        bookSpecification.setLanguage("En");
        bookSpecification.setEdition("3rd");
        bookSpecificationRepository.save(bookSpecification);
        List<BookSpecification> bookSpecificationList = (List<BookSpecification>) bookSpecificationRepository.findAll();
        if(bookSpecificationList.contains(bookSpecification)) fail("Should not be saved");
    }
    @Test(expected = ConstraintViolationException.class)
    public void testBookNameEmpty() {
        BookSpecification bookSpecification =  new BookSpecification();
        bookSpecification.setName("");
        bookSpecification.setIsbn("1234123");
        bookSpecification.setLanguage("En");
        bookSpecification.setEdition("3rd");
        bookSpecificationRepository.save(bookSpecification);
        List<BookSpecification> bookSpecificationList = (List<BookSpecification>) bookSpecificationRepository.findAll();
        if(bookSpecificationList.contains(bookSpecification)) fail("Should not be saved");
    }
    @Test(expected = ConstraintViolationException.class)
    public void testBookNameLengthOne() {
        BookSpecification bookSpecification =  new BookSpecification();
        bookSpecification.setName("a");
        bookSpecification.setIsbn("1234123");
        bookSpecification.setLanguage("En");
        bookSpecification.setEdition("3rd");
        bookSpecificationRepository.save(bookSpecification);
        List<BookSpecification> bookSpecificationList = (List<BookSpecification>) bookSpecificationRepository.findAll();
        if(bookSpecificationList.contains(bookSpecification)) fail("Should not be saved");
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUniqueISBN() {
        BookSpecification bookSpecification =  new BookSpecification();
        bookSpecification.setName("Test Book");
        bookSpecification.setIsbn("121"); //  Not Unique
        bookSpecification.setLanguage("En");
        bookSpecification.setEdition("3rd");
        bookSpecificationRepository.save(bookSpecification);
        List<BookSpecification> bookSpecificationList = (List<BookSpecification>) bookSpecificationRepository.findAll();
        if(bookSpecificationList.contains(bookSpecification)) fail("Should not be saved");
    }





}
