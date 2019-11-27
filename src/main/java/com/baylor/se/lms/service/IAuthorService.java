package com.baylor.se.lms.service;

import com.baylor.se.lms.model.Author;

import java.util.List;

public interface IAuthorService {
    public Author registerAuthor(Author author);
    public Author getAuthor(Long id);
    public List<Author> getAuthors();
    public Author updateAuthor(Author author);
}
