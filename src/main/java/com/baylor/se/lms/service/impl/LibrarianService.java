package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.LibrarianRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Librarian Service class handles all CRUD operation for Librarian.
 * Implements IUserService interface
 */
@Service
@Slf4j
public class LibrarianService implements IUserService {
    @Autowired
    LibrarianRepository librarianRepository;
    @Autowired
    JmsTemplate jmsTemplate;

    /**
     * Creates User as Librarian Type
     * @param user : Librarian Details
     * @return Saved User
     */
    @Override
    public User registerUser(User user)
    {
        return librarianRepository.save((Librarian) user);
    }

    /**
     * Returns Librarian for given id. Handles exception if Librarian not found
     * @param id : Librarian Id
     * @return Librarian record
     */
    @Override
    public User getUser(Long id) {
        log.info("Getting librarian id : "+ id);
        return librarianRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * Returns all non-deleted Librarians
     * @return List of Librarians
     */
    @Override
    public List<User> getAll()
    {
        List<Librarian> allLibrarians =librarianRepository.findAllByDeleteFlagFalse();
        List<User> librarians = new ArrayList<>(allLibrarians);
        return librarians;
    }

    /**
     * Updates Librarian with provided id
     * @param user : Librarian
     * @param id : Librarian Id
     * @return Updated Librarian
     */
    @Override
    public User updateUser(User user, Long id){
        Librarian librarian = (Librarian)getUser(id);
        librarian = (Librarian) updateValuesUser(user, librarian);
        return librarianRepository.save(librarian);
    }

    /**
     * Soft delete Librarian Id
     * @param id : Librarian
     */
    @Override
    public void deleteUser(Long id) {
        log.info("Deleting Librarian: " + id);
        Librarian librarian = librarianRepository.findById(id).orElseThrow(NotFoundException::new);
        librarian.setDeleteFlag(true);
        librarianRepository.save(librarian);
        jmsTemplate.convertAndSend("post-librarian-delete", librarian);
    }

    /**
     *  postDelete converts deleted Librarian information
     * @param librarian : Deleted Librarian
     */
    @JmsListener(destination = "post-librarian-delete", containerFactory = "postDeleteFactory")
    public void postDelete(Librarian librarian) {
        convertAfterDelete(librarian);
        librarianRepository.save(librarian);
    }
}
