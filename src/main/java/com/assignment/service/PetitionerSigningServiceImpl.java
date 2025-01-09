package com.assignment.service;

import com.assignment.dto.SigningInRequest;
import com.assignment.dto.PetitionerDto;
import com.assignment.entity.PetitionerEntity;
import com.assignment.exception.DuplicateAccountException;
import com.assignment.exception.IncorrectPasswordException;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.exception.UserNotFoundException;
import com.assignment.mapper.PetitionerDtoEntityMapper;
import com.assignment.repository.PetitionerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PetitionerSigningServiceImpl implements PetitionerSigningService {

    private static final Logger log = LoggerFactory.getLogger(PetitionerSigningServiceImpl.class);

    private final PetitionerDtoEntityMapper petitionerDtoEntityMapper;

    private final PetitionerRepository petitionerRepository;

    public PetitionerSigningServiceImpl(PetitionerDtoEntityMapper petitionerDtoEntityMapper, PetitionerRepository petitionerRepository) {
        this.petitionerDtoEntityMapper = petitionerDtoEntityMapper;
        this.petitionerRepository = petitionerRepository;
    }


    @Override
    public PetitionerDto signUpPetitioner(PetitionerDto petitionerDto) throws DuplicateAccountException {
        if (petitionerRepository.findPetitionerByEmailId(petitionerDto.getEmailId()) != null) {
            log.error("Account with {} already exists.", petitionerDto.getEmailId());
            throw new DuplicateAccountException("Account with " + petitionerDto.getEmailId() + "already exists. Try again!");
        }

        PetitionerEntity petitionerEntity = petitionerDtoEntityMapper.convertToPetitionerEntity(petitionerDto);
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
