package com.baylor.se.lms.dto;

import com.baylor.se.lms.model.Author;
import com.baylor.se.lms.model.BookCategory;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for return search results.
 */
public class SearchDTO {

    private Long id;
    private String name;
    private String publication;
    private String edition;
    private String language;
    private String isbn;
    private Author author;
    private Set<BookCategory> bookCategorySet = new HashSet<>();

    private Set<Long> bookIds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<BookCategory> getBookCategorySet() {
        return bookCategorySet;
    }

    public void setBookCategorySet(Set<BookCategory> bookCategorySet) {
        this.bookCategorySet = bookCategorySet;
    }


    public Set<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(Set<Long> bookIds) {
        this.bookIds = bookIds;
    }
}
