package com.baylor.se.lms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="Book_Loan")
public class BookLoan implements Serializable {
    public enum LoanStatus {
        REQUESTED,
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

    @ManyToOne
    private Student requestedBy;

    @ManyToOne
    private Librarian issuedBy;

    @OneToOne
    private BookLog log;
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
}
