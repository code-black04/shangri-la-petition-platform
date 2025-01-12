package com.assignment.controller;

import com.assignment.auth.AuthRequestDTO;
import com.assignment.auth.JwtResponseDTO;
import com.assignment.dto.MessageDto;
import com.assignment.dto.SigningInRequest;
import com.assignment.dto.PetitionerDto;
import com.assignment.entity.PetitionerEntity;
import com.assignment.exception.DuplicateAccountException;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.repository.PetitionerRepository;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/petitioner/auth")
public class PetitionerSigningController {

    private static final Logger log = LoggerFactory.getLogger(PetitionerSigningController.class);

    private final PetitionerSigningService petitionerSigningService;

    private final BiometricIdManager biometricIdManager;

    private AuthenticationManager authenticationManager;

    @Autowired
    private PetitionerRepository userRepository;

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
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<MessageDto> signUpPetitioner(
            @RequestBody @Valid PetitionerDto petitionerDto
    ) {
        log.info("Request: {}", petitionerDto);

        String biometricId = petitionerDto.getBiometricId();

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


    @RequestMapping(path = "/logout", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<MessageDto> logoutUser(
            @RequestParam(required = false, name = "user") String user,
            HttpServletResponse response
    ) {
        log.info("User with email {} is trying to logout", user);
        clearCookie("accessToken", response);
     return new ResponseEntity<>(new MessageDto(HttpStatus.OK, "Logout successful"), HttpStatus.OK);
    }

    private void clearCookie(String cookieName, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
            cookie.setHttpOnly(false);
        cookie.setMaxAge(0);
        cookie.setDomain("localhost");
        cookie.setSecure(false);
        response.addCookie(cookie);
    }


    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost", allowCredentials = "true")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse
            response) {
        if(authRequestDTO.isAdmin()){
            PetitionerEntity user = userRepository.findPetitionerByEmailId(authRequestDTO.getUsername());
            if(user != null && !Boolean.TRUE.equals(user.isCommitteeAdmin())){
                throw new UnauthorizedAccessException("Unauthorized login attempt, user not a committee member");
            }
        } else {
            PetitionerEntity user = userRepository.findPetitionerByEmailId(authRequestDTO.getUsername());
            if(user != null && Boolean.TRUE.equals(user.isCommitteeAdmin())){
                throw new UnauthorizedAccessException("Unauthorized login attempt, user required to be petitioner.");
            }
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            String accessToken = jwtService.GenerateToken(authRequestDTO.getUsername(), authentication.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                        .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .domain("localhost")
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
