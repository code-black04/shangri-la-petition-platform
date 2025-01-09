package com.assignment.service;

import com.assignment.dto.SigningInRequest;
import com.assignment.entity.PetitionCommitteeEntity;
import com.assignment.exception.IncorrectPasswordException;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.exception.UserNotFoundException;
import com.assignment.mapper.PetitionCommitteeDtoEntityMapper;
import com.assignment.repository.PetitionCommitteeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PetitionCommitteeSigningServiceImpl implements PetitionCommitteeSigningService {

    private static final Logger log = LoggerFactory.getLogger(PetitionCommitteeSigningServiceImpl.class);

    private final PetitionCommitteeDtoEntityMapper petitionCommitteeDtoEntityMapper;

    private final PetitionCommitteeRepository petitionCommitteeRepository;

    public PetitionCommitteeSigningServiceImpl(PetitionCommitteeDtoEntityMapper petitionCommitteeDtoEntityMapper, PetitionCommitteeRepository petitionCommitteeRepository) {
        this.petitionCommitteeDtoEntityMapper = petitionCommitteeDtoEntityMapper;
        this.petitionCommitteeRepository = petitionCommitteeRepository;
    }

    @PostConstruct
    public void createDefaultPetitionCommitteeAdmin() {
        String adminEmail = "admin@petition.parliament.sr";
        //TODO String defaultPassword = "2025%shangrila";, this one has to be used while submitting the assignment
        String defaultPassword = "2025@shangrila";

        // Check if the admin already exists
        if (petitionCommitteeRepository.findPetitionCommitteeByEmailId(adminEmail) != null) {
            log.info("Default admin already exists: {}", adminEmail);
            return;
        }

        PetitionCommitteeEntity petitionCommitteeEntity = new PetitionCommitteeEntity();
        petitionCommitteeEntity.setEmailId(adminEmail);
        petitionCommitteeEntity.setPassword(defaultPassword);

        petitionCommitteeRepository.save(petitionCommitteeEntity);
        log.info("Default admin created successfully with email: {}", adminEmail);

    }

    @Override
    public Boolean signInPetitionCommittee(SigningInRequest signingInRequest) throws UserNotFoundException, IncorrectPasswordException, UnauthorizedAccessException {
        boolean isPetitionCommitteePresent = petitionCommitteeRepository.findPetitionCommitteeByEmailId(signingInRequest.getEmailId()) != null;
        if (isPetitionCommitteePresent) {
            boolean isPasswordMatching = petitionCommitteeRepository.findPetitionCommitteeByEmailId(signingInRequest.getEmailId()).getPassword().equals(signingInRequest.getPassword());
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
