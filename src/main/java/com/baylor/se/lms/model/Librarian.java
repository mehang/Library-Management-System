package com.baylor.se.lms.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
/**
 * Data model for librarian entity. Maps as  librarian in the database.
 */
@Entity
@Table(name="Librarian")
@DiscriminatorValue("LIBRARIAN")
public class Librarian extends User implements Serializable {
    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if(!(o instanceof Librarian)) {
            return false;
        }
        Librarian that = (Librarian) o;
        return (this.getId().equals(that.getId()) &&
                this.getUsername().equals(that.getUsername()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getUsername());
    }
}
