package com.assignment.controller;

import com.assignment.dto.MessageDto;
import com.assignment.dto.PetitionDto;
import com.assignment.dto.PetitionStatusEnum;
import com.assignment.exception.PetitionNotFoundException;
import com.assignment.service.PetitionService;
import io.swagger.models.Response;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/slpp")
public class PetitionController {

    private static final Logger log = LoggerFactory.getLogger(PetitionController.class);

    private final PetitionService petitionService;

    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    /**
     * Create petition with open status, number of signatures as 1, response as null by default.
     * @param petitionDto
     * @return
     */
    @RequestMapping(path = "/petitions/create",
            method = RequestMethod.POST,
            consumes = { "application/json" },
            produces = { "application/json" })
    public ResponseEntity<PetitionDto> createPetition(
            @RequestBody @Valid PetitionDto petitionDto
    ) {
        petitionDto.setPetitionStatusEnum(PetitionStatusEnum.OPEN);
        petitionDto.setSignature(1);
        petitionDto.setResponse(null);
        log.info("Request: {}", petitionDto);
        PetitionDto petitionCreated = petitionService.createPetition(petitionDto);
        ResponseEntity<PetitionDto> response = null;
        if (petitionCreated != null) {
            response = new ResponseEntity<>(petitionCreated, HttpStatus.CREATED);
            log.info("Response: {}", response);
        }
        return response;
    }

    @RequestMapping(path = "/petitions",
            method = RequestMethod.GET,
            produces = { "application/json" })
    public ResponseEntity<List<PetitionDto>> getAllPetitions() throws PetitionNotFoundException {
        List<PetitionDto> petitionDtoList = petitionService.getAllPetitions();

        if (petitionDtoList.isEmpty()) {
            log.error("No petition exists");
            throw new PetitionNotFoundException("No petition exists");
        }
        log.info("All Petitions provided");
        ResponseEntity<List<PetitionDto>> responseEntity = new ResponseEntity<>(petitionDtoList, HttpStatus.FOUND);
        return responseEntity;
    }

    @RequestMapping(path = "/petition", method = RequestMethod.GET,
                    produces = {"application/json"})
    public ResponseEntity<List<PetitionDto>> getAllPetitionsByStatus(@RequestParam String status) throws PetitionNotFoundException {
        List<PetitionDto> allPetitionByStatus = petitionService.getAllPetitionsByStatus(status);
        if (allPetitionByStatus == null) {
            log.error("No petition exist with {} status", status);
            throw new PetitionNotFoundException("No petition exist with " + status + " status");
        }

        ResponseEntity<List<PetitionDto>> responseEntity = new ResponseEntity<>(allPetitionByStatus, HttpStatus.FOUND);
        return responseEntity;
    }

}
