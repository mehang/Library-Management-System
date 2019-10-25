package com.baylor.se.lms.service;

import com.baylor.se.lms.data.AuthorRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.Author;
import com.baylor.se.lms.service.authorService.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AuthorService implements IAuthorService {
    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Author registerAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(NotFoundException::new);
        return author;
    }

    @Override
    public List<Author> getAuthors() {
        List<Author> authors = (List<Author>) authorRepository.findAll();
        return authors;
    }

    @Override
    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }
}
