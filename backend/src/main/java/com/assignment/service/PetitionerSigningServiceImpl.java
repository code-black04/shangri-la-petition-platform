package com.assignment.service;

import com.assignment.dto.SigningInRequest;
import com.assignment.dto.PetitionerDto;
import com.assignment.entity.PetitionerEntity;
import com.assignment.exception.*;
import com.assignment.mapper.PetitionerDtoEntityMapper;
import com.assignment.repository.PetitionerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PetitionerSigningServiceImpl implements PetitionerSigningService {

    private static final Logger log = LoggerFactory.getLogger(PetitionerSigningServiceImpl.class);

    private final PetitionerDtoEntityMapper petitionerDtoEntityMapper;

    private final PetitionerRepository petitionerRepository;
    @Autowired
    private PasswordEncoder encoder;
    public PetitionerSigningServiceImpl(PetitionerDtoEntityMapper petitionerDtoEntityMapper, PetitionerRepository petitionerRepository) {
        this.petitionerDtoEntityMapper = petitionerDtoEntityMapper;
        this.petitionerRepository = petitionerRepository;
    }

    @PostConstruct
    public void createDefaultPetitionCommitteeAdmin() {
        String adminEmail = "admin@petition.parliament.sr";
        //TODO String defaultPassword = "2025%shangrila";, this one has to be used while submitting the assignment
        String defaultPassword = "2025%shangrila";

        // Check if the admin already exists
        if (petitionerRepository.findPetitionerByEmailId(adminEmail) != null) {
            log.info("Default admin already exists: {}", adminEmail);
            return;
        }

        PetitionerEntity petitionCommitteeEntity = new PetitionerEntity();
        petitionCommitteeEntity.setEmailId(adminEmail);
        petitionCommitteeEntity.setCommitteeAdmin(true);
        petitionCommitteeEntity.setPassword(encoder.encode(defaultPassword));

        petitionerRepository.save(petitionCommitteeEntity);
        log.info("Default admin created successfully with email: {}", adminEmail);

    }

    @Override
    public PetitionerDto signUpPetitioner(PetitionerDto petitionerDto) throws DuplicateAccountException {
        if (petitionerRepository.findPetitionerByEmailId(petitionerDto.getEmailId()) != null) {
            log.error("Account with {} already exists. ", petitionerDto.getEmailId());
            throw new DuplicateAccountException("Account with " + petitionerDto.getEmailId() + " already exists. Try again!");
        }

        if (petitionerRepository.findPetitionerByBiometricId(petitionerDto.getBiometricId()) != null) {
            log.error("Account with {} already exists. ", petitionerDto.getBiometricId());
            throw new DuplicateBiometricException("Account with " + petitionerDto.getBiometricId() + " already in use. Check back!");
        }

        PetitionerEntity petitionerEntity = petitionerDtoEntityMapper.convertToPetitionerEntity(petitionerDto);
        petitionerEntity.setPassword(encoder.encode(petitionerDto.getPassword()));
        petitionerRepository.save(petitionerEntity);
        log.info("User {} successfully signed up", petitionerDto.getEmailId());
        return petitionerDtoEntityMapper.convertToPetitionerDto(petitionerEntity);
    }

    @Override
    public Boolean signInPetitioner(SigningInRequest signingInRequest) throws UserNotFoundException, IncorrectPasswordException, UnauthorizedAccessException {
        boolean isPetitionerPresent = petitionerRepository.findPetitionerByEmailId(signingInRequest.getEmailId()) != null;
        if (isPetitionerPresent) {
            boolean isPasswordMatching = petitionerRepository.findPetitionerByEmailId(signingInRequest.getEmailId()).getPassword().equals(signingInRequest.getPassword());
            if (isPasswordMatching) {
                return true;
            } else {
                log.error("Incorrect password for user login: {}", signingInRequest.getEmailId());
                throw new IncorrectPasswordException("Incorrect password");
            }
        } else {
            log.error("Incorrect isPetitionerPresent: {}", signingInRequest.getEmailId());
            throw new UserNotFoundException("Incorrect emailId");
        }
    }
}
