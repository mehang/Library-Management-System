package com.baylor.se.lms.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Data model for Admin Entity. It extends User entity. This class is mapped as table  admin  in the database.
 *
 */
@Entity
@Table(name="Admin")
@DiscriminatorValue("ADMIN")
public class Admin extends User implements Serializable {
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
