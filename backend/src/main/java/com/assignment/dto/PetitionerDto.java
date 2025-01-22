package com.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class PetitionerDto {

    @NotNull
    @NotEmpty
    @JsonProperty(value = "firstName", required = true)
    private String firstName;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "lastName", required = true)
    private String lastName;

    @JsonProperty(value = "dateOfBirth", required = true)
    private LocalDate dateOfBirth;

    @Email
    @NotEmpty
    @JsonProperty(value = "emailId", required = true)
    private String emailId;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 30)
    @JsonProperty(value = "password", required = true)
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d)(?=.*[^a-zA-Z0-9]).{8,20}$")
    private String password;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "biometricId", required = true)
    private String biometricId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

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

    public String getBiometricId() {
        return biometricId;
    }

    public void setBiometricId(String biometricId) {
        this.biometricId = biometricId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PetitionerDto that)) return false;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(emailId, that.emailId) && Objects.equals(password, that.password) && Objects.equals(biometricId, that.biometricId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth, emailId, password, biometricId);
    }

    @Override
    public String toString() {
        return "PetitionerDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", biometricId='" + biometricId + '\'' +
                '}';
    }
}
