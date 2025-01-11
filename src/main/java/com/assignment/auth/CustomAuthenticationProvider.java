package com.assignment.auth;

import com.assignment.controller.PetitionerSigningController;
import com.assignment.dto.SigningInRequest;
import com.assignment.exception.UnauthorizedAccessException;
import com.assignment.service.PetitionerSigningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

            return new UsernamePasswordAuthenticationToken(
                username, 
                password, 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
