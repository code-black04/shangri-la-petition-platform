package com.assignment.service;

import com.assignment.dto.PetitionCommitteeDecision;
import com.assignment.dto.PetitionDto;
import com.assignment.dto.PetitionStatusEnum;
import com.assignment.entity.PetitionEntity;
import com.assignment.exception.BadRequestException;
import com.assignment.exception.DuplicateAccountException;
import com.assignment.exception.PetitionNotFoundException;
import com.assignment.mapper.PetitionDtoEntityMapper;
import com.assignment.repository.PetitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetitionCommitteeServiceImpl implements PetitionCommitteeService{

    private static final Logger log = LoggerFactory.getLogger(PetitionCommitteeServiceImpl.class);

    private final PetitionDtoEntityMapper petitionDtoEntityMapper;

    private final PetitionRepository petitionRepository;

    public PetitionCommitteeServiceImpl(PetitionDtoEntityMapper petitionDtoEntityMapper, PetitionRepository petitionRepository) {
        this.petitionDtoEntityMapper = petitionDtoEntityMapper;
        this.petitionRepository = petitionRepository;
    }

    @Override
    public PetitionDto updatePetitionWithStatusAndResponse(Integer petitionId, PetitionCommitteeDecision petitionCommitteeDecision) throws DuplicateAccountException {
        PetitionEntity existingPetition = petitionRepository.findById(petitionId)
                .orElseThrow(() -> new PetitionNotFoundException("Petition not found"));

        if (existingPetition.getPetitionStatusEnum().equals(PetitionStatusEnum.CLOSED)) {
            throw new BadRequestException("Closed Petition cannot be updated further.");
        }

        if (existingPetition.getSignature() < existingPetition.getSignatureThreshold()) {
            throw new BadRequestException("Require at least " + existingPetition.getSignatureThreshold() + " signature to close the petition");
        }
        
        existingPetition.setPetitionStatusEnum(petitionCommitteeDecision.getPetitionStatusEnum());
        existingPetition.setResponse(petitionCommitteeDecision.getResponse());

        petitionRepository.save(existingPetition);

        PetitionDto petitionDto = petitionDtoEntityMapper.convertToPetitionDto(existingPetition);
        return petitionDto;

    }

    @Override
    public Boolean updateSignatureThresholdForPetitions(Integer signatureThreshold) {
        List<PetitionEntity> openPetitions = petitionRepository.findAllPetitionByPetitionStatusEnum(String.valueOf(PetitionStatusEnum.OPEN));

        if (openPetitions.isEmpty()) {
            return false; // No petitions to update
        }

        for (PetitionEntity petition : openPetitions) {
            petition.setSignatureThreshold(signatureThreshold); // Update the threshold
        }

        petitionRepository.saveAll(openPetitions); // Save the updated petitions
        return true;
    }
}
