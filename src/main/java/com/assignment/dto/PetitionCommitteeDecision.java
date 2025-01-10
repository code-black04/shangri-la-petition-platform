package com.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PetitionCommitteeDecision {

    @NotNull
    @JsonProperty(value = "status")
    private PetitionStatusEnum petitionStatusEnum;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "response")
    private String response;

    public PetitionStatusEnum getPetitionStatusEnum() {
        return petitionStatusEnum;
    }

    public void setPetitionStatusEnum(PetitionStatusEnum petitionStatusEnum) {
        this.petitionStatusEnum = petitionStatusEnum;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "PetitionCommitteeDecision{" +
                "petitionStatusEnum=" + petitionStatusEnum +
                ", response='" + response + '\'' +
                '}';
    }
}
