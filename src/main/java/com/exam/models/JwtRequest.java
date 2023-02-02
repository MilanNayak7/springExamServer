package com.exam.models;

import java.io.Serial;
import java.io.Serializable;

public class JwtRequest implements Serializable {

    private String username;
    private String password;

    public JwtRequest() {

    }

    public JwtRequest(String username, String password) {

        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
