package com.baylor.se.lms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="Book_Loan")
public class BookLoan implements Serializable {
    public enum LoanStatus {
        REQUESTED,
        ISSUED,
        GRANTED,
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfRequest;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfReturn;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date actualDateOfReturn;

    @ManyToOne
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    private Student requestedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    private Librarian issuedBy;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<BookLog> log = new HashSet<>();


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

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if(!(o instanceof BookLoan)) {
            return false;
        }
        BookLoan that = (BookLoan) o;
        /**
         * todo: add student unique attribute too
         */
        return (this.getId().equals(that.getId()) &&
                this.getDateOfRequest().equals(that.getDateOfRequest()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getDateOfRequest());
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Date getActualDateOfReturn() {
        return actualDateOfReturn;
    }

    public void setActualDateOfReturn(Date actualDateOfReturn) {
        this.actualDateOfReturn = actualDateOfReturn;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Student getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Student requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Librarian getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(Librarian issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Set<BookLog> getLog() {
        return log;
    }

    public void setLog(Set<BookLog> log) {
        this.log = log;
    }
}
