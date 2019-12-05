package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.dto.factory.LibrarianFactory;
import com.baylor.se.lms.model.Librarian;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class LibrarianService implements IUserService {
    @Autowired
    LibrarianRepository librarianRepository;
    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public User registerUser(User user)
    {
        return librarianRepository.save((Librarian) user);
    }

    @Override
    public User getUser(Long id) {
        log.info("Getting librarian id : "+ id);
        return librarianRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<User> getAll()
    {
        List<Librarian> allLibrarians =librarianRepository.findAllByDeleteFlagFalse();
        List<User> librarians = new ArrayList<>(allLibrarians);
        return librarians;
    }

    @Override
    public User updateUser(User user, Long id){
        Librarian librarian = (Librarian)getUser(id);
        librarian.setEmail(user.getEmail());
        librarian.setName(user.getName());
        librarian.setUsername(user.getUsername());
        librarian.setPhoneNumber(user.getPhoneNumber());
        return librarianRepository.save(librarian);
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

}
