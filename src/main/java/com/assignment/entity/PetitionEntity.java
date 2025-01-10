package com.assignment.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Table
@Entity(name = "petition_entity")
public class PetitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "petitioner_seq")
    @SequenceGenerator(name = "petitioner_seq", sequenceName = "petitioner_sequence", allocationSize = 1)
    @Column(name = "petitioner_id", updatable = false, unique = true)
    private Integer petitionerId;

    @Column(name = "status")
    private String petitionStatusEnum;

    @Column(name = "petition_date", updatable = false)
    @CreatedDate
    private LocalDate petitionDate;

    @Column(name = "petition_title", updatable = false)
    private String petitionTitle;

    @Column(name = "petition_text", updatable = false)
    private String petitionText;

    @Column(name = "petitioner", updatable = false)
    private String petitioner;

    @Column(name = "signature")
    private Integer signature;

    @Column(name = "response")
    private String response;

    public Integer getPetitionerId() {
        return petitionerId;
    }

    public void setPetitionerId(Integer petitionerId) {
        this.petitionerId = petitionerId;
    }

    public String getPetitionStatusEnum() {
        return petitionStatusEnum;
    }

    public void setPetitionStatusEnum(String petitionStatusEnum) {
        this.petitionStatusEnum = petitionStatusEnum;
    }

    public LocalDate getPetitionDate() {
        return petitionDate;
    }

    public void setPetitionDate(LocalDate petitionDate) {
        this.petitionDate = petitionDate;
    }

    public String getPetitionTitle() {
        return petitionTitle;
    }

    public void setPetitionTitle(String petitionTitle) {
        this.petitionTitle = petitionTitle;
    }

    public String getPetitionText() {
        return petitionText;
    }

    public void setPetitionText(String petitionText) {
        this.petitionText = petitionText;
    }

    public String getPetitioner() {
        return petitioner;
    }

    public void setPetitioner(String petitioner) {
        this.petitioner = petitioner;
    }

    public Integer getSignature() {
        return signature;
    }

    public void setSignature(Integer signature) {
        this.signature = signature;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
