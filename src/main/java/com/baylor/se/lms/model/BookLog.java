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
        ISSUED,
        REQUEST
    }
    @Id
    @GeneratedValue
    private Long id;

    private Action action;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    @OneToOne
    private BookLoan bookLoan;


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

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public BookLoan getBookLoan() {
        return bookLoan;
    }

    public void setBookLoan(BookLoan bookLoan) {
        this.bookLoan = bookLoan;
    }
}
