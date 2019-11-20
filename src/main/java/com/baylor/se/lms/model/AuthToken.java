package com.baylor.se.lms.model;

import java.io.Serializable;

public class AuthToken implements Serializable {

    private String token;

    public AuthToken(){

    }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}