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

    @Column(unique = true)
    private String ISBN;

    @ManyToOne
    private Author author;

    @ManyToMany
    private Set<BookCategory> bookCategorySet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getISBN(){
        return this.ISBN;
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
                this.getISBN().equals(that.getISBN()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getISBN());
    }

}
