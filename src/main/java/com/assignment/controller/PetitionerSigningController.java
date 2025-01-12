package com.assignment.controller;

import com.assignment.dto.MessageDto;
import com.assignment.dto.SigningInRequest;
import com.assignment.dto.PetitionerDto;
import com.assignment.exception.DuplicateAccountException;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.service.PetitionerSigningService;
import com.assignment.utils.BiometricIdManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/petitioner/auth")
public class PetitionerSigningController {

    private static final Logger log = LoggerFactory.getLogger(PetitionerSigningController.class);

    private final PetitionerSigningService petitionerSigningService;

    private final BiometricIdManager biometricIdManager;

    public PetitionerSigningController(PetitionerSigningService petitionerSigningService,
                                       BiometricIdManager biometricIdManager) {
        this.petitionerSigningService = petitionerSigningService;
        this.biometricIdManager = biometricIdManager;
    }

    @RequestMapping(path = "/signup",
            method = RequestMethod.POST,
            consumes = { "application/json" },
            produces = { "application/json" })
    public ResponseEntity<MessageDto> signUpPetitioner(
            @RequestBody @Valid PetitionerDto petitionerDto
    ) {
        log.info("Request: {}", petitionerDto);

        String biometricId = petitionerDto.getBiometricId();

        // Check if the BioID is already used
        if (biometricIdManager.isBioIdUsed(biometricId)) {
            log.error("BioID already used: {}", biometricId);
            return new ResponseEntity<>(
                    new MessageDto(HttpStatus.CONFLICT, "BioID already used: " + biometricId),
                    HttpStatus.CONFLICT
            );
        }

        // Check if the BioID is valid and exists
        if (!biometricIdManager.isBioIdValid(biometricId)) {
            log.error("Biometric ID '{}' is invalid or not found", biometricId);
            return new ResponseEntity<>(
                    new MessageDto(HttpStatus.NOT_FOUND, "Biometric ID not found: " + biometricId),
                    HttpStatus.NOT_FOUND
            );
        }



        PetitionerDto userDto = petitionerSigningService.signUpPetitioner(petitionerDto);

        if (userDto != null) {
            // Use the BioID (move it to the used set)
            biometricIdManager.useBiometricId(biometricId);
            log.info("Signup successful for: {}", petitionerDto.getEmailId());
            return new ResponseEntity<>(
                    new MessageDto(HttpStatus.CREATED, "Signup successful for: " + petitionerDto.getEmailId()),
                    HttpStatus.CREATED
            );
        } else {
            log.error("Unsuccessful signup attempt for: {}", petitionerDto.getEmailId());
            return new ResponseEntity<>(
                    new MessageDto(HttpStatus.BAD_REQUEST, "Unsuccessful signup attempt"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @RequestMapping(path = "/login",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<MessageDto> signInUser(
            @RequestBody @Valid SigningInRequest signingInRequest
            ) {
        log.info("User with {} is trying to login", signingInRequest.getEmailId());

        try {
            Boolean isSignInSuccessful =  petitionerSigningService.signInPetitioner(signingInRequest);
            if (Boolean.TRUE.equals(isSignInSuccessful)) {
                MessageDto message = new MessageDto(
                        HttpStatus.OK,
                        "Successful login by " + signingInRequest.getEmailId()
                );
                log.info("Login successful for email '{}'", signingInRequest.getEmailId());
                return ResponseEntity.ok(message);
            } else {
                log.error("Login failed for email '{}': Unauthorized attempt", signingInRequest.getEmailId());
                throw new UnauthorizedAccessException("Invalid credentials provided");
            }
        } catch (DuplicateAccountException ex) {
            log.error("Duplicate account error: {}", ex.getMessage());
            return new ResponseEntity<>(
                    new MessageDto(HttpStatus.CONFLICT, ex.getMessage()),
                    HttpStatus.CONFLICT
            );
        } catch (Exception e) {
            log.error("An error occurred during login for email '{}': {}", signingInRequest.getEmailId(), e.getMessage(), e);
            MessageDto errorMessage = new MessageDto(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred. Please try again later."
            );
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<MessageDto> logoutUser(
            @RequestBody @Valid SigningInRequest signingInRequest,
            HttpServletResponse response
    ) {
        log.info("User with email {} is trying to logout", signingInRequest.getEmailId());

        // Clear cookies
        clearCookie("accessToken", response);
        clearCookie("refreshToken", response); // Add other cookies if necessary

        // Return response
        return new ResponseEntity<>(new MessageDto(HttpStatus.OK, "Logout successful"), HttpStatus.OK);
    }

    private void clearCookie(String cookieName, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // Expire the cookie immediately
        cookie.setSecure(true); // Ensure it's secure if using HTTPS
        response.addCookie(cookie);
    }
}
