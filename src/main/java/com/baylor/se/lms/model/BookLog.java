package com.baylor.se.lms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="Book_Log")
public class BookLog implements Serializable {
    public enum Action{
        RETURN,
        BORROW
    }
    @Id
    @GeneratedValue
    private Long id;

    private Action action;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    @OneToOne
    private BookLoan bookLoan;

    @ManyToOne
    private Book book;

    public Long getId() {
        return id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if(!(o instanceof BookLog)) {
            return false;
        }
        BookLog that = (BookLog) o;
        return (this.getId().equals(that.getId()) &&
                this.getTimeStamp().equals(that.getTimeStamp()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getTimeStamp());
    }
}
