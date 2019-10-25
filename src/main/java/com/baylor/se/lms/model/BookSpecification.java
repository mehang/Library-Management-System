package com.baylor.se.lms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="Book_Specification")
public class BookSpecification implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;


    private String publication;
    private String edition;
    private String language;

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

    @Column(unique = true)
    private String isbn;

    @ManyToOne
    private Author author;

    @ManyToMany
    private Set<BookCategory> bookCategorySet = new HashSet<>();

    @Column
    private boolean deleteFlag;

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if(!(o instanceof BookSpecification)) {
            return false;
        }
        BookSpecification that = (BookSpecification) o;
        return (this.getId().equals(that.getId()) &&
                this.getIsbn().equals(that.getIsbn()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getIsbn());
    }

}
