package com.assignment.mapper;

import com.assignment.dto.PetitionDto;
import com.assignment.dto.PetitionSigningUserDto;
import com.assignment.dto.PetitionerDto;
import com.assignment.entity.PetitionEntity;
import com.assignment.entity.PetitionSigningUserEntity;
import com.assignment.entity.PetitionerEntity;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetitionDtoEntityMapper {

    @Autowired
    public ModelMapper modelMapper;

    public PetitionDtoEntityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    public PetitionDto convertToPetitionDto(PetitionEntity petitionEntity) {
        Hibernate.initialize(petitionEntity.getPetitionSigningUserEntityList());
        PetitionDto petitionDto = modelMapper.map(petitionEntity, PetitionDto.class);

        // Manually map the list of signing users
        List<PetitionSigningUserDto> signingUserDtos = petitionEntity.getPetitionSigningUserEntityList().stream()
                .map(userEntity -> modelMapper.map(userEntity, PetitionSigningUserDto.class))
                .collect(Collectors.toList());
        petitionDto.setPetitionSigningUserEntityList(signingUserDtos);

        return petitionDto;
    }

    public PetitionEntity convertToPetitionEntity(PetitionDto petitionDto) {
        PetitionEntity petitionEntity = modelMapper.map(petitionDto, PetitionEntity.class);
        return petitionEntity;
    }

    private void configureModelMapper() {
        modelMapper.typeMap(PetitionEntity.class, PetitionDto.class).addMappings(mapper -> {
            mapper.skip(PetitionDto::setPetitionSigningUserEntityList); // Avoid default mapping
        });

        modelMapper.typeMap(PetitionSigningUserEntity.class, PetitionSigningUserDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getId().getPetition_id(), PetitionSigningUserDto::setPetition_id);
            mapper.map(src -> src.getId().getEmailId(), PetitionSigningUserDto::setEmailId);
        });
    }

}
