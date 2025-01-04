package com.assignment.service;

import com.assignment.dto.PetitionDto;
import com.assignment.entity.PetitionEntity;
import com.assignment.mapper.PetitionDtoEntityMapper;
import com.assignment.repository.PetitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PetitionServiceImpl implements PetitionService{

    private static final Logger log = LoggerFactory.getLogger(PetitionerSigningServiceImpl.class);

    private final PetitionDtoEntityMapper petitionDtoEntityMapper;

    private final PetitionRepository petitionRepository;

    public PetitionServiceImpl(PetitionDtoEntityMapper petitionDtoEntityMapper, PetitionRepository petitionRepository) {
        this.petitionDtoEntityMapper = petitionDtoEntityMapper;
        this.petitionRepository = petitionRepository;
    }

    @Override
    public PetitionDto createPetition(PetitionDto petitionDto) {
        PetitionEntity petitionEntity = petitionDtoEntityMapper.convertToPetitionEntity(petitionDto);
        petitionRepository.save(petitionEntity);
        log.info("Petition with petition id {} created successfully", petitionDto.getPetitionId());
        return petitionDtoEntityMapper.convertToPetitionDto(petitionEntity);
    }
}
