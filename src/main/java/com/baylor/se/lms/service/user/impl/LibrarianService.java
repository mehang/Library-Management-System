package com.baylor.se.lms.service.user.impl;

import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.data.LibrarianRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibrarianService implements IUserService {
    @Autowired
    LibrarianRepository librarianRepository;

    @Override
    public void registerUser(User user)
    {
        librarianRepository.save((Librarian)user);
    }

    @Override
    public User getUser(Long id){
        Librarian librarian =   librarianRepository.findById(id).orElseThrow(NotFoundException::new);
        return librarian;
    }

    @Override
    public void updateUser(User user){
        librarianRepository.save((Librarian) user);
    }

    @Override
    public void deleteUser(Long id){
        Librarian librarian =   librarianRepository.findById(id).orElseThrow(NotFoundException::new);
        librarian.setDeleteFlag(true);
        librarianRepository.save(librarian);
    }
}
