package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.AuthorRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.Author;
import com.baylor.se.lms.service.IAuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@Slf4j
public class AuthorService implements IAuthorService {

    @Autowired
    AuthorRepository authorRepository;
    @Override
    public Author registerAuthor(Author author) {
        log.info("Registering Author : " + author.getName());
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthor(Long id) {
        log.info("Retrieving Author : " + id);
        return authorRepository.findAuthorById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Author> getAuthors() {
        log.info("Retrieving All Authors");
        List <Author> authors = authorRepository.findAllByDeleteFlagFalse();
        return authors;
    }

    @Override
    public Author updateAuthor(Author author) {
        log.info("Updating Authors: "+ author.getName());
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Deleting Author with id : "+ id );
        Author author = getAuthor(id);
        author.setDeleteFlag(true);
        updateAuthor(author);
    }
}
