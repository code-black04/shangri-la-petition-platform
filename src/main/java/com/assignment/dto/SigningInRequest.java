package com.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.util.Objects;

public class SigningInRequest {

    @Email
    @NotEmpty
    @JsonProperty(value = "emailId", required = true)
    private String emailId;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "password", required = true)
    private String password;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SigningInRequest that)) return false;
        return Objects.equals(emailId, that.emailId) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId, password);
    }

    @Override
    public String toString() {
        return "SigningInRequest{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
