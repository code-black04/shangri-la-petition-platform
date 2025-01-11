package com.assignment.service;

import com.assignment.dto.PetitionDto;
import com.assignment.dto.PetitionStatusEnum;
import com.assignment.entity.PetitionEntity;
import com.assignment.entity.PetitionSigningUserEntity;
import com.assignment.entity.PetitionSigningUserId;
import com.assignment.exception.BadRequestException;
import com.assignment.exception.PetitionNotFoundException;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.mapper.PetitionDtoEntityMapper;
import com.assignment.repository.PetitionRepository;
import com.assignment.repository.PetitionerRepository;
import javassist.NotFoundException;
import org.hibernate.Hibernate;
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

    private final PetitionerRepository petitionerRepository;

    public PetitionServiceImpl(PetitionDtoEntityMapper petitionDtoEntityMapper, PetitionRepository petitionRepository, PetitionerRepository petitionerRepository) {
        this.petitionDtoEntityMapper = petitionDtoEntityMapper;
        this.petitionRepository = petitionRepository;
        this.petitionerRepository = petitionerRepository;
    }

    @Override
    public PetitionDto createPetition(PetitionDto petitionDto) {
        boolean isPetitioner = isPetitionerExists(petitionDto.getPetitioner());
        if (isPetitioner) {
            if (petitionDto.getPetitionId() != null && !petitionRepository.findById(petitionDto.getPetitionId()).isEmpty()) {
                throw new BadRequestException("Petition with petitionId " + petitionDto.getPetitionId() + " cannot be updated");
            }
            PetitionEntity petitionEntity = petitionDtoEntityMapper.convertToPetitionEntity(petitionDto);
            petitionRepository.save(petitionEntity);
            log.info("Petition with petition id {} created successfully", petitionDto.getPetitionId());
            return petitionDtoEntityMapper.convertToPetitionDto(petitionEntity);
        } else {
            log.error("Petitioner does not exists with email Id provided");
            throw new UnauthorizedAccessException("Petitioner does not exists with email Id provided");
        }
    }

    @Override
    public List<PetitionDto> getAllPetitions() throws PetitionNotFoundException {
        List<PetitionEntity> petitionEntities = petitionRepository.findAll();
        if (petitionEntities.isEmpty()) {
            log.error("No petition exists");
            throw new PetitionNotFoundException("No petition exists");
        }

        petitionEntities.forEach(petition -> Hibernate.initialize(petition.getPetitionSigningUserEntityList()));
        return petitionEntities.stream()
                .map(petitionDtoEntityMapper::convertToPetitionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetitionDto> getAllPetitionsByStatus(String petitionStatus) throws PetitionNotFoundException {
        PetitionStatusEnum statusEnum = PetitionStatusEnum.fromValue(petitionStatus);
        List<PetitionEntity> petitionEntities = petitionRepository.findAllByPetitionStatusEnum(statusEnum);

        if (petitionEntities.isEmpty()) {
            log.error("No petition exist with {} status", petitionStatus);
            throw new PetitionNotFoundException("No petition exist with " + petitionStatus + " status");
        }
        return petitionEntities.stream()
                .map(petitionDtoEntityMapper::convertToPetitionDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean signOpenPetition(Integer petitionId, String emailId) {
        PetitionEntity existingPetition = petitionRepository.findByPetitionId(petitionId);

        if (existingPetition == null) {
            log.error("Petition does not exists");
            throw new PetitionNotFoundException("Petition does not exists");
        } else {
            if (existingPetition.getPetitionStatusEnum().equals(PetitionStatusEnum.OPEN)) {

                boolean isPetitioner = isPetitionerExists(emailId);
                if (isPetitioner) {
                    boolean isPetitionAlreadySigned = existingPetition.getPetitionSigningUserEntityList().stream()
                            .anyMatch(user -> user.getId().getEmailId().equals(emailId));

                    if (isPetitionAlreadySigned) {
                        log.warn("Email ID {} has already signed the petition.", emailId);
                        throw new BadRequestException("You have already signed this petition.");
                    }

                    // Add new signing user to the list
                    PetitionSigningUserEntity newSigningUser = new PetitionSigningUserEntity();
                    newSigningUser.setId(new PetitionSigningUserId(petitionId, emailId));
                    existingPetition.getPetitionSigningUserEntityList().add(newSigningUser);
                    petitionRepository.save(existingPetition);

                    log.info("Email ID {} successfully signed the petition with ID {}.", emailId, petitionId);
                    return true;
                } else {
                    log.error("Petitioner does not exists with email Id provided");
                    throw new UnauthorizedAccessException("Petitioner does not exists with email Id provided");
                }
            } else {
                log.error("Signing attempt for closed petition declined.");
                throw new BadRequestException("Signing attempt for closed petition declined.");
            }
        }
    }

    private boolean isPetitionerExists(String emailId) {
        boolean isPetitioner = false;
        if (petitionerRepository.findPetitionerByEmailId(emailId) != null) {
            isPetitioner = true;
        }
        return isPetitioner;
    }
}
