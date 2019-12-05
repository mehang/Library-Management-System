package com.baylor.se.lms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="Student")
@DiscriminatorValue("STUDENT")
public class Student extends User implements Serializable {


    @Column
    private String degree;

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(!(o instanceof Student)) {
            return false;
        }
        Student that = (Student) o;
        return (this.getId().equals(that.getId()) &&
                this.getUsername().equals(that.getUsername()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getUsername());
    }
}
