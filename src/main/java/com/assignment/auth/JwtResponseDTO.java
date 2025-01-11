package com.assignment.auth;

public class JwtResponseDTO {

    private String accessToken;
    private String token;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}