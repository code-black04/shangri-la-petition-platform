package com.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class PetitionSigningUserDto {

    private Integer petition_id;

    private String emailId;

    public PetitionSigningUserDto() {
    }

    public PetitionSigningUserDto(Integer petition_id, String emailId) {
        this.petition_id = petition_id;
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getPetition_id() {
        return petition_id;
    }

    public void setPetition_id(Integer petition_id) {
        this.petition_id = petition_id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PetitionSigningUserDto that)) return false;
        return Objects.equals(petition_id, that.petition_id) && Objects.equals(emailId, that.emailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petition_id, emailId);
    }

    @Override
    public String toString() {
        return "PetitionSigningUserDto{" +
                "petition_id=" + petition_id +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
