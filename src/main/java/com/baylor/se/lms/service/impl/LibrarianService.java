package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.dto.factory.LibrarianFactory;
import com.baylor.se.lms.exception.UnmatchingPasswordException;
import com.baylor.se.lms.model.Author;
import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.data.LibrarianRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class LibrarianService implements IUserService {
    @Autowired
    LibrarianRepository librarianRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    LibrarianFactory librarianFactory;

    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public User registerUser(UserDTO userDTO)
    {
        if (!userDTO.getPassword1().equals(userDTO.getPassword2())) {
            throw new UnmatchingPasswordException("Password 1 and password 2 don't match with each other.");
        }
        log.info("Registering Student: "+ userDTO.getUsername());
        User user = librarianFactory.getUser(userDTO);
        log.info("Saving student : " + user.getUsername());
        return librarianRepository.save((Librarian) user);
    }

    @Override
    public User getUser(Long id) {
        log.info("Getting librarian id : "+ id);
        Librarian librarian = librarianRepository.findById(id).orElseThrow(NotFoundException::new);
        return librarian;
    }

    public List<Librarian> getAll() {
        log.info("Get All Librarians: ");
        List<Librarian> librarians = (List<Librarian>) librarianRepository.findAll();
        return librarians;
    }

    @Override
    public User updateUser(User user) {
        log.info("Updating Librarian: " +user.getUsername());
        return librarianRepository.save((Librarian) user);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting Librarian: " + id);
        Librarian librarian = librarianRepository.findById(id).orElseThrow(NotFoundException::new);
        librarian.setDeleteFlag(true);
        librarianRepository.save(librarian);
        jmsTemplate.convertAndSend("post-librarian-delete", librarian);
    }

    @JmsListener(destination = "post-librarian-delete", containerFactory = "postDeleteFactory")
    public void postDelete(Librarian librarian) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        librarian.setUsername(librarian.getUsername()+timeStamp);
        librarian.setPhoneNumber(String.format ("%010d", librarian.getId()));
        librarian.setEmail("deleted"+librarian.getId()+"@gmail.com");
        librarianRepository.save(librarian);
    }

    public void changePassword(long id, String newPassword){

        Librarian librarian = librarianRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("Changing Password: " +  librarian.getUsername());
        librarian.setPassword(bcryptEncoder.encode(newPassword));
        librarianRepository.save(librarian);
    }
}
