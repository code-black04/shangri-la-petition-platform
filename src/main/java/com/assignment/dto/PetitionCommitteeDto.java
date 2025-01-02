package com.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;

public class PetitionCommitteeDto {

    @JsonIgnore
    private UUID petitionCommitteeId;

    @Email
    @NotEmpty
    @JsonProperty(value = "emailId", required = true)
    private String emailId;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 20)
    @JsonProperty(value = "password", required = true)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d)(?=.*[^a-zA-Z0-9]).{8,20}$")
    private String password;

    public UUID getPetitionCommitteeId() {
        return petitionCommitteeId;
    }

    public void setPetitionCommitteeId(UUID petitionCommitteeId) {
        this.petitionCommitteeId = petitionCommitteeId;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PetitionCommitteeDto that)) return false;
        return Objects.equals(petitionCommitteeId, that.petitionCommitteeId) && Objects.equals(emailId, that.emailId) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petitionCommitteeId, emailId, password);
    }

    @Override
    public String toString() {
        return "PetitionCommitteeDto{" +
                "petitionCommitteeId=" + petitionCommitteeId +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
