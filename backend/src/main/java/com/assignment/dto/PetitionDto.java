package com.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PetitionDto {

    @JsonProperty(value = "petition_id")
    private Integer petitionId;

    @JsonProperty(value = "status")
    private PetitionStatusEnum petitionStatusEnum;

    @JsonProperty(value = "petition_date", required = true)
    private LocalDate petitionDate;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "petition_title", required = true)
    private String petitionTitle;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "petition_text", required = true)
    private String petitionText;

    @JsonProperty(value = "petitioner")
    private String petitioner;

    @JsonProperty(value = "signature")
    private Integer signature;

    @JsonProperty(value = "response")
    private String response;

    @JsonProperty(value = "signature_threshold")
    private Integer signatureThreshold;

    @JsonProperty("petitionSigningUserEntityList")
    private List<PetitionSigningUserDto> petitionSigningUserEntityList = new ArrayList<>();

    public Integer getPetitionId() {
        return petitionId;
    }

    public void setPetitionId(Integer petitionId) {
        this.petitionId = petitionId;
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

    public List<PetitionSigningUserDto> getPetitionSigningUserEntityList() {
        return petitionSigningUserEntityList;
    }

    public void setPetitionSigningUserEntityList(List<PetitionSigningUserDto> petitionSigningUserEntityList) {
        this.petitionSigningUserEntityList = petitionSigningUserEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PetitionDto that)) return false;
        return Objects.equals(petitionId, that.petitionId) && petitionStatusEnum == that.petitionStatusEnum && Objects.equals(petitionDate, that.petitionDate) && Objects.equals(petitionTitle, that.petitionTitle) && Objects.equals(petitionText, that.petitionText) && Objects.equals(petitioner, that.petitioner) && Objects.equals(signature, that.signature) && Objects.equals(response, that.response) && Objects.equals(signatureThreshold, that.signatureThreshold) && Objects.equals(petitionSigningUserEntityList, that.petitionSigningUserEntityList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petitionId, petitionStatusEnum, petitionDate, petitionTitle, petitionText, petitioner, signature, response, signatureThreshold, petitionSigningUserEntityList);
    }

    @Override
    public String toString() {
        return "PetitionDto{" +
                "petitionId=" + petitionId +
                ", petitionStatusEnum=" + petitionStatusEnum +
                ", petitionDate=" + petitionDate +
                ", petitionTitle='" + petitionTitle + '\'' +
                ", petitionText='" + petitionText + '\'' +
                ", petitioner='" + petitioner + '\'' +
                ", signature=" + signature +
                ", response='" + response + '\'' +
                ", signatureThreshold=" + signatureThreshold +
                ", petitionSigningUserEntityList=" + petitionSigningUserEntityList +
                '}';
    }
}
