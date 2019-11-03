package com.baylor.se.lms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="Book_Category")
public class BookCategory implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany
    private Set<BookSpecification> bookSpecificationSet = new HashSet<>();

    @Column(columnDefinition = "BOOLEAN")
    private boolean deleteFlag = false;

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if(!(o instanceof BookCategory)) {
            return false;
        }
        BookCategory that = (BookCategory) o;
        return (this.getId().equals(that.getId()) &&
                this.getName().equals(that.getName()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName());
    }
}
