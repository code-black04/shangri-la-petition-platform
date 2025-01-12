package com.assignment.controller;

import com.assignment.dto.MessageDto;
import com.assignment.dto.PetitionDto;
import com.assignment.dto.PetitionStatusEnum;
import com.assignment.exception.BadRequestException;
import com.assignment.exception.PetitionNotFoundException;
import com.assignment.service.PetitionService;
import io.swagger.models.Response;
import javassist.NotFoundException;
import org.mockito.internal.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/slpp")
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
            @RequestBody @Valid PetitionDto petitionDto, Authentication authentication
    ) {
        if (petitionDto.getPetitionStatusEnum() != null
            && petitionDto.getPetitionStatusEnum().equals(PetitionStatusEnum.CLOSED)) {
            throw new BadRequestException("The petition cannot be closed upon creation. Please set the status to 'Open'.");
        }
        if (petitionDto.getResponse() != null && !petitionDto.getResponse().isEmpty()) {
            throw  new BadRequestException("Only the petition committee can publish a response. Please leave the response field empty.");
        }
        petitionDto.setPetitionStatusEnum(PetitionStatusEnum.OPEN);
        petitionDto.setPetitioner(authentication.getName());
        petitionDto.setSignature(0);
        petitionDto.setResponse(null);
        petitionDto.setPetitionDate(LocalDate.now());
        log.info("Request: {}", petitionDto);
        PetitionDto petitionCreated = petitionService.createPetition(petitionDto);
        ResponseEntity<PetitionDto> response = null;
        if (petitionCreated != null) {
            log.info("Petition successfully created: {}", petitionCreated);
            response = new ResponseEntity<>(petitionCreated, HttpStatus.CREATED);
        } else {
            log.warn("Petition creation failed. No petition was created.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @RequestMapping(path = "/petitions", method = RequestMethod.GET,
                    produces = {"application/json"})
    public ResponseEntity<Map<String, List<PetitionDto>>> getAllPetitionsByStatus(@RequestParam(required = false) String status) throws PetitionNotFoundException {

        List<PetitionDto> petitionDtoList = new ArrayList<>();

        if (StringUtils.isEmpty(status)) {
            petitionDtoList = petitionService.getAllPetitions();
        } else {
            petitionDtoList = petitionService.getAllPetitionsByStatus(status);
        }

        if (petitionDtoList == null) {
            log.info("No petition exist..");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Map<String, List<PetitionDto>> listMap = new HashMap<>();
        listMap.put("petitions", petitionDtoList);

        ResponseEntity<Map<String, List<PetitionDto>>> responseEntity = new ResponseEntity<>(listMap, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(path = "/petitions/signature",
                    method = RequestMethod.PUT,
                    produces = "application/json")
    public ResponseEntity<MessageDto> signOpenPetition(
            @RequestParam Integer petitionId,
            @RequestParam String emailId,
            Authentication authentication
    ) {
        boolean signedSuccessfully = petitionService.signOpenPetition(petitionId, authentication.getName());
        if (!signedSuccessfully) {
            log.error("Un-successful signature  attempt");
            throw new BadRequestException("Un-successful signature attempt");
        }
        MessageDto message = new MessageDto(HttpStatus.OK, "Successful signature attempt");
        ResponseEntity<MessageDto> response = new ResponseEntity<>(message, HttpStatus.OK);
        log.info("Response: {}", response);
        return response;
    }

}
