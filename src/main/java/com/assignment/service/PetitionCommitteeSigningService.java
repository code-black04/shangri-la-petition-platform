package com.assignment.service;

import com.assignment.dto.SigningInRequest;
import com.assignment.exception.IncorrectPasswordException;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.exception.UserNotFoundException;

public interface PetitionCommitteeSigningService {

    Boolean signInPetitionCommittee (SigningInRequest signingInRequest) throws UserNotFoundException, IncorrectPasswordException, UnauthorizedAccessException;
}
