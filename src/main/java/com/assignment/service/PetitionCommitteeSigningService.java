package com.assignment.service;

import com.assignment.exception.IncorrectPasswordException;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.exception.UserNotFoundException;

public interface PetitionCommitteeSigningService {

    Boolean signInPetitionCommittee (String emailId, String password) throws UserNotFoundException, IncorrectPasswordException, UnauthorizedAccessException;
}
