package com.baylor.se.lms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Data model for Book Entity. Mapped as table "Book" in the database.
 */
@Entity
@Table(name="Book")
public class Book implements Serializable {
    public enum BookStatus {
        AVAILABLE,
        NOT_AVAILABLE,
    }

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private String serialNo;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Librarian updatedBy;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private BookSpecification specification;


    @Column(columnDefinition = "BOOLEAN")
    private boolean deleteFlag = false;

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Book(){}
    public Book(BookStatus status){
        this.status = status;
    }

public Long getId() {
        return this.id;
}

public BookStatus getStatus() {
        return this.status;
}

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if(!(o instanceof Book)) {
            return false;
        }
        Book that = (Book) o;
        /**
         * todo: add unique book specification
         */
        return (this.getId().equals(that.getId()) &&
                this.getStatus().equals(that.getStatus()));
    }

    @Override
    public int hashCode() {
        /**
         * todo: add unique book specification
         */
        return Objects.hash(this.getId(), this.getStatus());
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Librarian getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Librarian updatedBy) {
        this.updatedBy = updatedBy;
    }

    public BookSpecification getSpecification() {
        return specification;
    }

    public void setSpecification(BookSpecification specification) {
        this.specification = specification;
    }
}
