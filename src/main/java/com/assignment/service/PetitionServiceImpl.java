package com.assignment.service;

import com.assignment.dto.PetitionDto;
import com.assignment.dto.PetitionStatusEnum;
import com.assignment.entity.PetitionEntity;
import com.assignment.exception.PetitionNotFoundException;
import com.assignment.mapper.PetitionDtoEntityMapper;
import com.assignment.repository.PetitionRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<PetitionDto> getAllPetitions() throws PetitionNotFoundException {
        List<PetitionEntity> petitionEntities = petitionRepository.findAll();
        if (petitionEntities.isEmpty()) {
            log.error("No petition exists");
            throw new PetitionNotFoundException("No petition exists");
        }
        return petitionEntities.stream()
                .map(petitionDtoEntityMapper::convertToPetitionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetitionDto> getAllPetitionsByStatus(String petitionStatus) throws PetitionNotFoundException {
        PetitionStatusEnum statusEnum = PetitionStatusEnum.fromValue(petitionStatus);

        List<PetitionEntity> petitionEntities = petitionRepository.findAllPetitionByPetitionStatusEnum(String.valueOf(statusEnum));

        if (petitionEntities.isEmpty()) {
            log.error("No petition exist with {} status", petitionStatus);
            throw new PetitionNotFoundException("No petition exist with " + petitionStatus + " status");
        }
        return petitionEntities.stream()
                .map(petitionDtoEntityMapper::convertToPetitionDto)
                .collect(Collectors.toList());
    }
}
