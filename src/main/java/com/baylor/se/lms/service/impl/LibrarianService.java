package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.data.LibrarianRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianService implements IUserService {
    @Autowired
    LibrarianRepository librarianRepository;

    @Override
    public User registerUser(User user) {
        return librarianRepository.save((Librarian) user);
    }

    @Override
    public User getUser(Long id) {
        Librarian librarian = librarianRepository.findById(id).orElseThrow(NotFoundException::new);
        return librarian;
    }

    public List<Librarian> getAll() {
        List<Librarian> librarians = (List<Librarian>) librarianRepository.findAll();
        return librarians;
    }

    @Override
    public User updateUser(User user) {
        return librarianRepository.save((Librarian) user);
    }

    @Override
    public void deleteUser(Long id) {
        Librarian librarian = librarianRepository.findById(id).orElseThrow(NotFoundException::new);
        librarian.setDeleteFlag(true);
        librarianRepository.save(librarian);
    }
}
