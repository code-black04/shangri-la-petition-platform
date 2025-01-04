package com.assignment.mapper;

import com.assignment.dto.PetitionDto;
import com.assignment.dto.PetitionerDto;
import com.assignment.entity.PetitionEntity;
import com.assignment.entity.PetitionerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetitionDtoEntityMapper {

    @Autowired
    public ModelMapper modelMapper;

    public PetitionDto convertToPetitionDto(PetitionEntity petitionEntity) {
        PetitionDto petitionDto = modelMapper.map(petitionEntity, PetitionDto.class);
        return petitionDto;
    }

    public PetitionEntity convertToPetitionEntity(PetitionDto petitionDto) {
        PetitionEntity petitionEntity = modelMapper.map(petitionDto, PetitionEntity.class);
        return petitionEntity;
    }
}
