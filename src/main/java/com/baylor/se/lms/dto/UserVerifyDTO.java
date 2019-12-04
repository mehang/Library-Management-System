package com.baylor.se.lms.dto;

public class UserVerifyDTO {
    Long userId;
    Boolean  verified;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
