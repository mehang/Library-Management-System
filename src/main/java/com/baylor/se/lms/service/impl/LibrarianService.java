package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.data.LibrarianRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Override
    public User registerUser(User user)
    {
        log.info("Registering Librarian: " + user.getUsername());
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        Role role = new Role();
        role.setRole("LIBRARIAN");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        log.info("Creating  Librarian....");
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
    }

    public void changePassword(long id, String newPassword){

        Librarian librarian = librarianRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("Changing Password: " +  librarian.getUsername());
        librarian.setPassword(bcryptEncoder.encode(newPassword));
        librarianRepository.save(librarian);
    }
}
