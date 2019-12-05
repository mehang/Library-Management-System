package com.baylor.se.lms.dto.user.update;

public class StudentUpdateDTO extends UserUpdateDTO {
    private String degree;

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
