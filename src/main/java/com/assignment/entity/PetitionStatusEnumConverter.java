package com.assignment.entity;

import com.assignment.dto.PetitionStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PetitionStatusEnumConverter implements AttributeConverter<PetitionStatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(PetitionStatusEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toValue();
    }

    @Override
    public PetitionStatusEnum convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return PetitionStatusEnum.fromValue(dbData);
    }
}
