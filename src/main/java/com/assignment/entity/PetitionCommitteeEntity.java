package com.assignment.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table
@Entity(name = "petition_committee_entity")
public class PetitionCommitteeEntity {

    @Id
    @Column(name = "petition_committee_id", columnDefinition = "VARCHAR(255)", updatable = false, unique = true)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID petitionerId;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "password")
    private String password;

    public UUID getPetitionerId() {
        return petitionerId;
    }

    public void setPetitionerId(UUID petitionerId) {
        this.petitionerId = petitionerId;
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

}
