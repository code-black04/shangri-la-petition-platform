package com.assignment.controller;

import com.assignment.dto.MessageDto;
import com.assignment.dto.PetitionDto;
import com.assignment.service.PetitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/slpp/petitions")
public class PetitionController {

    private static final Logger log = LoggerFactory.getLogger(PetitionController.class);

    private final PetitionService petitionService;

    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    @RequestMapping(path = "/",
            method = RequestMethod.POST,
            consumes = { "application/json" },
            produces = { "application/json" })
    public ResponseEntity<MessageDto> createPetition(
            @RequestBody @Valid PetitionDto petitionDto
    ) {
        log.info("Request: {}", petitionDto);//Mask password value from user to be logged from
        PetitionDto petitionCreated = petitionService.createPetition(petitionDto);
        ResponseEntity<MessageDto> response = null;
        if (petitionCreated != null) {
            MessageDto message = new MessageDto(HttpStatus.CREATED, "Created new Petition");
            response = new ResponseEntity<>(message, HttpStatus.CREATED);
            log.info("Response: {}", response);
        }
        return response;
    }
}
