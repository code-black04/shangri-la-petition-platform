package com.assignment.controller;

import com.assignment.dto.MessageDto;
import com.assignment.dto.PetitionCommitteeDecision;
import com.assignment.dto.PetitionDto;
import com.assignment.dto.PetitionStatusEnum;
import com.assignment.exception.BadRequestException;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.service.PetitionCommitteeService;
import com.assignment.service.PetitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/slpp")
public class PetitionCommitteeController {

    private static final Logger log = LoggerFactory.getLogger(PetitionCommitteeController.class);

    private final PetitionCommitteeService petitionCommitteeService;

    public PetitionCommitteeController(PetitionCommitteeService petitionCommitteeService) {
        this.petitionCommitteeService = petitionCommitteeService;
    }

    /**
     * Update petition with closed status and response when threshold value
     * is matched with number of signatures for the petition has been received
     * @param
     * @return
     */
    @RequestMapping(path = "/petition/{petitionId}/update",
            method = RequestMethod.PUT,
            consumes = { "application/json" },
            produces = { "application/json" })
    public ResponseEntity<PetitionDto> createPetition(
            @PathVariable Integer petitionId,
            @RequestBody @Valid PetitionCommitteeDecision petitionCommitteeDecision
    ) {
        if (petitionCommitteeDecision.getPetitionStatusEnum().equals(PetitionStatusEnum.OPEN))
            throw new BadRequestException("The petition status cannot be updated to 'Open'. Please set the status to 'Closed'.");
        log.info("Request: {}", petitionCommitteeDecision);
        PetitionDto petitionUpdated = petitionCommitteeService.updatePetitionWithStatusAndResponse(petitionId, petitionCommitteeDecision);
        ResponseEntity<PetitionDto> response = null;
        if (petitionUpdated != null) {
            log.info("Petition ID {} successfully updated: {}", petitionId, petitionUpdated);
            response = new ResponseEntity<>(petitionUpdated, HttpStatus.CREATED);
        } else {
            log.warn("Petition update failed for ID {}. No changes were made.", petitionId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Update Threshold signature update
     * @param threshold
     * @return
     */
    @RequestMapping(path = "/petition/threshold",
                    method = RequestMethod.PUT,
                    produces = { "application/json" })
    public ResponseEntity<MessageDto> updateSignatureThresholdForPetitions(
            @RequestParam Integer threshold
    ) {
        Boolean updatedThreshold = petitionCommitteeService.updateSignatureThresholdForPetitions(threshold);
        if (!updatedThreshold) {
            log.error("Un-successful signature threshold attempt");
            throw new BadRequestException("Un-successful signature threshold attempt");
        }
        MessageDto message = new MessageDto(HttpStatus.OK, "Successful signature threshold attempt");
        ResponseEntity<MessageDto> response = new ResponseEntity<>(message, HttpStatus.OK);
        log.info("Response: {}", response);
        return response;
    }

    @RequestMapping(path = "/petition/threshold", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<Integer> getCurrentThreshold() {
        Integer currentThreshold = petitionCommitteeService.getCurrentThreshold();
        if (currentThreshold == null) {
            throw new BadRequestException("No signature threshold set yet");
        }
        log.info("Response: {}", currentThreshold);
        return new ResponseEntity<>(currentThreshold, HttpStatus.OK);
    }
}
