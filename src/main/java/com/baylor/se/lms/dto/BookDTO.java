package com.baylor.se.lms.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Book DTO for creation and update of books.
 */
public class BookDTO {
    private Long bookId;
    private Long librarianId;
    private String name;
    private String publication;
    private String edition;
    private String language;
    private String isbn;
    private Long authorId;
    private Set<Long> bookCategory =  new HashSet<>();

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setLibrarianId(Long librarianId) {
        this.librarianId = librarianId;
    }


    public long getLibrarianId() {
        return librarianId;
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

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public Set<Long> getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(Set<Long> bookCategory) {
        this.bookCategory = bookCategory;
    }
}
