package com.assignment.service;

import com.assignment.dto.PetitionCommitteeDecision;
import com.assignment.dto.PetitionDto;
import com.assignment.dto.PetitionerDto;
import com.assignment.exception.DuplicateAccountException;

public interface PetitionCommitteeService {

    PetitionDto updatePetitionWithStatusAndResponse (Integer petitionId, PetitionCommitteeDecision petitionCommitteeDecision) throws DuplicateAccountException;

    Boolean updateSignatureThresholdForPetitions(Integer signatureThreshold);

    Integer getCurrentThreshold();
}
