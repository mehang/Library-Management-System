package com.baylor.se.lms;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="Author")
public class Author implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    public Long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
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
