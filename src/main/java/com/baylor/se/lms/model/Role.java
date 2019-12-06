package com.baylor.se.lms.model;


import javax.persistence.*;

/**
 * Data model for Role entity. It maps as role table in the database.
 * Role is used to separate permission in our application
 */
@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role")
    private String role;

    public Role() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
