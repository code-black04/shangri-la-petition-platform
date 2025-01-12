package com.assignment.entity;

import com.assignment.dto.PetitionStatusEnum;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity(name = "petition_entity")
public class PetitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "petition_seq")
    @SequenceGenerator(name = "petition_seq", sequenceName = "petition_sequence", allocationSize = 1)
    @Column(name = "petition_id", updatable = false, unique = true)
    private Integer petitionId;

    @Column(name = "status")
    @Convert(converter = PetitionStatusEnumConverter.class)
    private PetitionStatusEnum petitionStatusEnum;

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

    @Column(name = "signature_threshold")
    private Integer signatureThreshold;

    @OneToMany(mappedBy = "petition", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @CollectionTable(name = "petition_signing_users", joinColumns = @JoinColumn(name = "petition_id"))
    private List<PetitionSigningUserEntity> petitionSigningUserEntityList = new ArrayList<>();

    public Integer getPetitionId() {
        return petitionId;
    }

    public void setPetitionId(Integer petitionerId) {
        this.petitionId = petitionerId;
    }

    public PetitionStatusEnum getPetitionStatusEnum() {
        return petitionStatusEnum;
    }

    public void setPetitionStatusEnum(PetitionStatusEnum petitionStatusEnum) {
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

    public Integer getSignatureThreshold() {
        return signatureThreshold;
    }

    public void setSignatureThreshold(Integer signatureThreshold) {
        this.signatureThreshold = signatureThreshold;
    }

    public List<PetitionSigningUserEntity> getPetitionSigningUserEntityList() {
        return petitionSigningUserEntityList;
    }

    public void setPetitionSigningUserEntityList(List<PetitionSigningUserEntity> petitionSigningUserEntityList) {
        this.petitionSigningUserEntityList = petitionSigningUserEntityList;
    }
}
