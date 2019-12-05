package com.baylor.se.lms.dto.user.create;

public class StudentCreateDTO extends UserCreateDTO {
    private String degree;

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
