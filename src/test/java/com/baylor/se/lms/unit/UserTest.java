package com.baylor.se.lms.unit;

import com.baylor.se.lms.data.LibrarianRepository;
import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.service.impl.LibrarianService;
import com.baylor.se.lms.service.impl.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private LibrarianRepository librarianRepository;
    @Test
    public void  testUserWithSameEmail(){

    }

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
    @Test(expected = Exception.class)
    public void testTestLibrarianUniqueEmail(){
        Librarian librarian = new Librarian();
        librarian.setUsername("Arun");
        librarian.setPassword("hello");
        librarian.setEmail("asanjel@mtu.edu"); // Not Uniquq
        librarian.setName("Arun Sanjel");
        librarian.setPhoneNumber("9063693028");
        List<Librarian> librarianList = librarianRepository.findAllByDeleteFlagFalse();
        if (librarianList.contains(librarian)) fail("Not Here");

    }
}
