package com.assignment.mapper;

import com.assignment.dto.PetitionCommitteeDto;
import com.assignment.dto.PetitionerDto;
import com.assignment.entity.PetitionCommitteeEntity;
import com.assignment.entity.PetitionerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetitionCommitteeDtoEntityMapper {

    @Autowired
    public ModelMapper modelMapper;

    public PetitionCommitteeDto convertToPetitionerDto(PetitionCommitteeEntity petitionCommitteeEntity) {
        PetitionCommitteeDto petitionCommitteeDto = modelMapper.map(petitionCommitteeEntity, PetitionCommitteeDto.class);
        return petitionCommitteeDto;
    }

    public PetitionCommitteeEntity convertToPetitionerEntity(PetitionCommitteeDto petitionCommitteeDto) {
        PetitionCommitteeEntity petitionCommitteeEntity = modelMapper.map(petitionCommitteeDto, PetitionCommitteeEntity.class);
        return petitionCommitteeEntity;
    }
}
