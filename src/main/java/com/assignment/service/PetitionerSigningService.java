package com.assignment.service;

import com.assignment.dto.PetitionerDto;
import com.assignment.exception.DuplicateAccountException;
import com.assignment.exception.IncorrectPasswordException;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.exception.UserNotFoundException;

public interface PetitionerSigningService {

    PetitionerDto signUpPetitioner (PetitionerDto petitionerDto) throws DuplicateAccountException;

    Boolean signInPetitioner (String emailId, String password) throws UserNotFoundException, IncorrectPasswordException, UnauthorizedAccessException;
}
