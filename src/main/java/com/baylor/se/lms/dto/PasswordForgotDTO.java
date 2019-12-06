package com.baylor.se.lms.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * DTO for Password Forget
 */
public class PasswordForgotDTO {
    @Email
    @NotEmpty
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
