package com.assignment.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PetitionSigningUserId implements Serializable {

    private Integer petition_id;
    private String emailId;

    public PetitionSigningUserId() {
    }

    public PetitionSigningUserId(Integer petition_id, String emailId) {
        this.petition_id = petition_id;
        this.emailId = emailId;
    }

    public Integer getPetition_id() {
        return petition_id;
    }

    public void setPetition_id(Integer petition_id) {
        this.petition_id = petition_id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PetitionSigningUserId that)) return false;
        return Objects.equals(petition_id, that.petition_id) && Objects.equals(emailId, that.emailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petition_id, emailId);
    }
}

