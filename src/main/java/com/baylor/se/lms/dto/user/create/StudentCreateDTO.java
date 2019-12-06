package com.baylor.se.lms.dto.user.create;

/**
 * Student Creation DTO
 */
public class StudentCreateDTO extends UserCreateDTO {
    private String degree;

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
