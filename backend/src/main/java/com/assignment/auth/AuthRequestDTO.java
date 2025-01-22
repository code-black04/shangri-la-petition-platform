package com.assignment.auth;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthRequestDTO {

    private String username;
    private String password;
    @JsonProperty(value = "isAdmin")
    private boolean isAdmin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

