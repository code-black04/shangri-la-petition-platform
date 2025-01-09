package com.assignment.controller;

import com.assignment.dto.MessageDto;
import com.assignment.dto.SigningInRequest;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.service.PetitionCommitteeSigningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/petition-committee/auth")
public class PetitionCommitteeSigningController {

    private static final Logger log = LoggerFactory.getLogger(PetitionCommitteeSigningController.class);

    private final PetitionCommitteeSigningService petitionerCommitteeSigningService;

    public PetitionCommitteeSigningController(PetitionCommitteeSigningService petitionCommitteeSigningService) {
        this.petitionerCommitteeSigningService = petitionCommitteeSigningService;
    }

    @RequestMapping(path = "/login",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<MessageDto> signInPetitionCommittee(
            @RequestBody SigningInRequest signingInRequest
            ) {
        log.info("User with {} is trying to login", signingInRequest.getEmailId());
        Boolean isSignInSuccessful =  petitionerCommitteeSigningService.signInPetitionCommittee(signingInRequest);
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
