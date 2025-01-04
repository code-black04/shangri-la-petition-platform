package com.assignment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PetitionStatusEnum {
    OPEN("open"),
    CLOSED("closed"),
    PENDING("pending");

    private final String value;

    PetitionStatusEnum(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PetitionStatusEnum fromValue(String value) {
        for (PetitionStatusEnum status : PetitionStatusEnum.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value for PetitionStatusEnum: " + value);
    }

    @JsonValue
    public String toValue() {
        return value;
    }
}
