package com.assignment.service;

import com.assignment.auth.SLPPAuthUser;
import com.assignment.entity.PetitionerEntity;
import com.assignment.repository.PetitionerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SLPPUserDetailsService implements UserDetailsService {

    @Autowired
    private PetitionerRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        PetitionerEntity user = userRepository.findPetitionerByEmailId(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new SLPPAuthUser(user);
    }
}