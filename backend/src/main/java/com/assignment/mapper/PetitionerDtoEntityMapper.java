package com.assignment.mapper;

import com.assignment.dto.PetitionerDto;
import com.assignment.entity.PetitionerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetitionerDtoEntityMapper {

    @Autowired
    public ModelMapper modelMapper;

    public PetitionerDto convertToPetitionerDto(PetitionerEntity petitionerEntity) {
        PetitionerDto petitionerDto = modelMapper.map(petitionerEntity, PetitionerDto.class);
        return petitionerDto;
    }

    public PetitionerEntity convertToPetitionerEntity(PetitionerDto petitionerDto) {
        PetitionerEntity petitionerEntity = modelMapper.map(petitionerDto, PetitionerEntity.class);
        return petitionerEntity;
    }
}
