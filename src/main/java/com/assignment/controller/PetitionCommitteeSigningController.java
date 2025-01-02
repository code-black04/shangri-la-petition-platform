package com.assignment.controller;

import com.assignment.dto.MessageDto;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.service.PetitionCommitteeSigningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @NonNull @RequestParam String emailId,
            @NonNull @RequestParam String password
    ) {
        log.info("User with {} is trying to login", emailId);
        Boolean isSignInSuccessful =  petitionerCommitteeSigningService.signInPetitionCommittee(emailId, password);
        if (!isSignInSuccessful) {
            log.error("Sign in was not successful");
            throw new UnauthorizedAccessException("Unauthorized login attempt");
        }
        MessageDto message = new MessageDto(HttpStatus.OK, "Successful login by " + emailId);
        ResponseEntity<MessageDto> response = new ResponseEntity<>(message, HttpStatus.OK);
        log.info("Response: {}", response);
        return response;
    }
}
