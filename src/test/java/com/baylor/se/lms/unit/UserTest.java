package com.baylor.se.lms.unit;

import com.baylor.se.lms.data.LibrarianRepository;
import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.service.impl.UserService;
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
public class UserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Test(expected = DataIntegrityViolationException.class)
    public void testLibrarianNotUniqueNumber(){
        Librarian librarian = new Librarian();
        librarian.setUsername("Arun");
        librarian.setPassword("hello");
        //librarian.setPassword2("hello");
        librarian.setEmail("sanjelarun@gmail.com");
        librarian.setName("Arun Sanjel");
        librarian.setPhoneNumber("9063693028"); // Not Unique
        librarianRepository.save(librarian);
        List<Librarian> librarianList = librarianRepository.findAllByDeleteFlagFalse();
        if (librarianList.contains(librarian)) fail("Not Here");

    }
    @Test(expected = DataIntegrityViolationException.class)
    public void testLibrarianNoUniqueEmail(){
        Librarian librarian = new Librarian();
        librarian.setUsername("Arun");
        librarian.setPassword("hello");
        librarian.setEmail("asanjel@mtu.edu"); // Not Unique
        librarian.setName("Arun Sanjel");
        librarian.setPhoneNumber("9063693028");
        librarianRepository.save(librarian);
        List<Librarian> librarianList = librarianRepository.findAllByDeleteFlagFalse();
        if (librarianList.contains(librarian)) fail("Should not be saved");
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testLibrarianNoUniqueUsername(){
        Librarian librarian = new Librarian();
        librarian.setUsername("asanjel"); // Not Unique
        librarian.setPassword("hello");
        librarian.setEmail("rai_ji@mtu.edu");
        librarian.setName("Arun Sanjel");
        librarian.setPhoneNumber("1233693028");
        librarianRepository.save(librarian);
        List<Librarian> librarianList = librarianRepository.findAllByDeleteFlagFalse();
        if (librarianList.contains(librarian)) fail("Should not be saved");
    }

    @Test(expected = ConstraintViolationException.class)
    public void testLibrarianInvalidPhoneNumber(){
        Librarian librarian = new Librarian();
        librarian.setUsername("rai_ji");
        librarian.setPassword("hello");
        librarian.setEmail("arun@mtu.edu");
        librarian.setName("Arun Sanjel");
        librarian.setPhoneNumber("90636930"); // Invalid Number
        librarianRepository.save(librarian);
        List<Librarian> librarianList = librarianRepository.findAllByDeleteFlagFalse();
        if (librarianList.contains(librarian)) fail("Should not be saved");
    }
    @Test(expected = ConstraintViolationException.class)
    public void testLibrarianInvalidEmail(){
        Librarian librarian = new Librarian();
        librarian.setUsername("rai_ji");
        librarian.setPassword("hello");
        librarian.setEmail("arun");
        librarian.setName("Arun Sanjel");
        librarian.setPhoneNumber("90636930"); // Invalid Number
        librarianRepository.save(librarian);
        List<Librarian> librarianList = librarianRepository.findAllByDeleteFlagFalse();
        if (librarianList.contains(librarian)) fail("Should not be saved");
    }
}
