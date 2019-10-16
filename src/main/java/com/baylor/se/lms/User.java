package com.baylor.se.lms;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@MappedSuperclass
public class User {
    @Column(unique=true, nullable = false)
    @NotBlank(message="Username is required")
    private String username;

    @Column(nullable = false)
    @NotBlank(message="Name is required")
    private String name;

    @Column(unique = true)
    @Pattern(regexp="(d{3})d{3}-d{4}")
    private String phoneNumber;

    private String password;

    @Column(unique = true)
    @Pattern(regexp = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}")
    private String email;

    public String getUsername(){
        return this.username;
    }
}
