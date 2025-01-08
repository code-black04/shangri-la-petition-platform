package com.assignment.service;

import com.assignment.dto.PetitionDto;
import com.assignment.exception.PetitionNotFoundException;
import javassist.NotFoundException;

import java.util.List;

public interface PetitionService {

    PetitionDto createPetition (PetitionDto petitionDto);

    List<PetitionDto> getAllPetitions() throws PetitionNotFoundException;

    List<PetitionDto> getAllPetitionsByStatus(String petitionStatus) throws PetitionNotFoundException;

}
