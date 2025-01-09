package com.assignment.controller;

import com.assignment.dto.MessageDto;
import com.assignment.dto.SigningInRequest;
import com.assignment.dto.PetitionerDto;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.service.PetitionerSigningService;
import com.assignment.utils.BiometricIdManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/petitioner/auth")
public class PetitionerSigningController {

    private static final Logger log = LoggerFactory.getLogger(PetitionerSigningController.class);

    private final PetitionerSigningService petitionerSigningService;

    private final BiometricIdManager biometricIdManager;

    public PetitionerSigningController(PetitionerSigningService petitionerSigningService,
                                       BiometricIdManager biometricIdManager) {
        this.petitionerSigningService = petitionerSigningService;
        this.biometricIdManager = biometricIdManager;
    }

    @RequestMapping(path = "/signup",
            method = RequestMethod.POST,
            consumes = { "application/json" },
            produces = { "application/json" })
    public ResponseEntity<MessageDto> signUpPetitioner(
            @RequestBody @Valid PetitionerDto petitionerDto
    ) {
        log.info("Request: {}", petitionerDto);

        String biometricId = petitionerDto.getBiometricId();

        if (!biometricIdManager.useBiometricId(biometricId)) {
            // Validate and remove the biometric ID
            if (!biometricIdManager.useBiometricId(biometricId)) {
                log.error("Invalid biometric ID: {}", biometricId);
                return new ResponseEntity<>(new MessageDto(HttpStatus.BAD_REQUEST, "Invalid biometric ID"), HttpStatus.BAD_REQUEST);
            }
        }

        PetitionerDto userDto = petitionerSigningService.signUpPetitioner(petitionerDto);
        ResponseEntity<MessageDto> response = null;
        if (userDto != null) {
            MessageDto message = new MessageDto(HttpStatus.CREATED, "Petitioner " + petitionerDto.getEmailId() + " has signed up successfully");
            response = new ResponseEntity<>(message, HttpStatus.CREATED);
            log.info("Response: {}", response);
        }
        return response;
    }

    @RequestMapping(path = "/login",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<MessageDto> signInUser(
            @RequestBody @Valid SigningInRequest signingInRequest
            ) {
        log.info("User with {} is trying to login", signingInRequest.getEmailId());
        Boolean isSignInSuccessful =  petitionerSigningService.signInPetitioner(signingInRequest);
        if (!isSignInSuccessful) {
            log.error("Sign in was not successful");
            throw new UnauthorizedAccessException("Unauthorized login attempt");
        }
        MessageDto message = new MessageDto(HttpStatus.OK, "Successful login by " + signingInRequest.getEmailId());
        ResponseEntity<MessageDto> response = new ResponseEntity<>(message, HttpStatus.OK);
        log.info("Response: {}", response);
        return response;
    }
}
