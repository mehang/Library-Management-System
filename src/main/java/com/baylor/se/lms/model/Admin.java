package com.baylor.se.lms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="Admin")
public class Admin extends User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;


    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if(!(o instanceof Admin)) {
            return false;
        }
        Admin that = (Admin) o;
        return (this.getId().equals(that.getId()) &&
                this.getUsername().equals(that.getUsername()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getUsername());
    }
}
