package com.baylor.se.lms.service;

import com.baylor.se.lms.model.Author;

import java.util.List;

/**
 * Interface for Author Services
 */
public interface IAuthorService {
    Author registerAuthor(Author author);
    Author getAuthor(Long id);
    List<Author> getAuthors();
    Author updateAuthor(Author author);
    void deleteAuthor(Long id);
}
