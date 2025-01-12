package com.assignment.auth;

import com.assignment.controller.PetitionerSigningController;
import com.assignment.dto.SigningInRequest;
import com.assignment.entity.PetitionerEntity;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.repository.PetitionerRepository;
import com.assignment.service.PetitionerSigningService;
import jdk.jfr.BooleanFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final PetitionerSigningService petitionerSigningService;
    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    @Autowired
    private PetitionerRepository userRepository;

    public CustomAuthenticationProvider(PetitionerSigningService petitionerSigningService) {
        this.petitionerSigningService = petitionerSigningService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        SigningInRequest signingInRequest = new SigningInRequest();
        signingInRequest.setEmailId(username);
        signingInRequest.setPassword(password);
        Boolean isSignInSuccessful =  petitionerSigningService.signInPetitioner(signingInRequest);
        if (!isSignInSuccessful) {
            log.error("Sign in was not successful");
            throw new UnauthorizedAccessException("Unauthorized login attempt");
        }
        // Replace with your custom authentication logic
        PetitionerEntity petitionerByEmailId = userRepository.findPetitionerByEmailId(signingInRequest.getEmailId());
        SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority(Boolean.FALSE.equals(petitionerByEmailId.isCommitteeAdmin()) ? "ROLE_USER" : "ROLE_COMMITTEE");
        return new UsernamePasswordAuthenticationToken(
                username, 
                password, 
                Collections.singletonList(roleUser)
            );

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}