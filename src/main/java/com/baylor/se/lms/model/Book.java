package com.baylor.se.lms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @ManyToOne
    private Librarian updatedBy;

    @ManyToOne
    private BookSpecification specification;


    @Column
    private boolean deleteFlag;

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

}
