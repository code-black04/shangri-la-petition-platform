package com.assignment.controller;

import com.assignment.auth.AuthRequestDTO;
import com.assignment.auth.JwtResponseDTO;
import com.assignment.dto.MessageDto;
import com.assignment.dto.SigningInRequest;
import com.assignment.dto.PetitionerDto;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.service.JwtService;
import com.assignment.service.PetitionerSigningService;
import com.assignment.utils.BiometricIdManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/petitioner/auth")
public class PetitionerSigningController {

    private static final Logger log = LoggerFactory.getLogger(PetitionerSigningController.class);

    private final PetitionerSigningService petitionerSigningService;

    private final BiometricIdManager biometricIdManager;

    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public PetitionerSigningController(PetitionerSigningService petitionerSigningService,
                                       BiometricIdManager biometricIdManager, AuthenticationManager authenticationManager) {
        this.petitionerSigningService = petitionerSigningService;
        this.biometricIdManager = biometricIdManager;
        this.authenticationManager = authenticationManager;
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

        if (!biometricIdManager.useBiometricId(biometricId)) {
            // Validate and remove the biometric ID
            if (!biometricIdManager.useBiometricId(biometricId)) {
                log.error("Invalid biometric ID: {}", biometricId);
                return new ResponseEntity<>(new MessageDto(HttpStatus.BAD_REQUEST, "Invalid biometric ID"), HttpStatus.BAD_REQUEST);
            }
        }

        PetitionerDto userDto = petitionerSigningService.signUpPetitioner(petitionerDto);
        ResponseEntity<MessageDto> response = null;
        if (userDto != null) {
            MessageDto message = new MessageDto(HttpStatus.CREATED, "Petitioner " + petitionerDto.getEmailId() + " has signed up successfully");
            response = new ResponseEntity<>(message, HttpStatus.CREATED);
            log.info("Response: {}", response);
        }
        return response;
    }

    @RequestMapping(path = "/login",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<MessageDto> signInUser(
            @RequestBody @Valid SigningInRequest signingInRequest
            ) {
        log.info("User with {} is trying to login", signingInRequest.getEmailId());
        Boolean isSignInSuccessful =  petitionerSigningService.signInPetitioner(signingInRequest);
        if (!isSignInSuccessful) {
            log.error("Sign in was not successful");
            throw new UnauthorizedAccessException("Unauthorized login attempt");
        }
        MessageDto message = new MessageDto(HttpStatus.OK, "Successful login by " + signingInRequest.getEmailId());
        ResponseEntity<MessageDto> response = new ResponseEntity<>(message, HttpStatus.OK);
        log.info("Response: {}", response);
        return response;
    }

    @RequestMapping(path = "/login2",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<MessageDto> signInUser(

    ) {
        MessageDto message = new MessageDto(HttpStatus.OK, "Successful login by ");
        ResponseEntity<MessageDto> response = new ResponseEntity<>(message, HttpStatus.OK);
        return response;
    }

    @PostMapping("/login3")
    @CrossOrigin(origins = "http://localhost", allowCredentials = "true")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){

            String accessToken = jwtService.GenerateToken(authRequestDTO.getUsername());
            // set accessToken to cookie header
            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .domain("localhost")
                    //.sameSite("None")
                    .maxAge(1800000)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            JwtResponseDTO jwt = new JwtResponseDTO();
            jwt.setAccessToken(accessToken);
            return jwt;

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }
}
