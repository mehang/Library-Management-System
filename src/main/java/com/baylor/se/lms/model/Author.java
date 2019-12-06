package com.baylor.se.lms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Data model for Author. This class is mapped as Author table is the database.
 */
@Entity
@Table(name="Author")
public class Author implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean deleteFlag = false;


    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if(!(o instanceof Author)) {
            return false;
        }
        Author that = (Author) o;
        return (this.getId().equals(that.getId()) &&
                this.getName().equals(that.getName()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName());
    }
}
