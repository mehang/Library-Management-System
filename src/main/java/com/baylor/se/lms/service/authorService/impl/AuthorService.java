package com.baylor.se.lms.service.authorService.impl;

import com.baylor.se.lms.data.AuthorRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.Author;
import com.baylor.se.lms.service.authorService.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class AuthorService implements IAuthorService {

    @Autowired
    AuthorRepository authorRepository;
    @Override
    public Author registerAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthor(Long id) {
        return authorRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Author> getAuthors() {
        List <Author> authors = (List<Author>) authorRepository.findAll();
        authors.removeIf(Author::isDeleteFlag);
        return authors;
    }

    @Override
    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }
}
