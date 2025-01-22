package com.assignment.auth;
import com.assignment.dto.PetitionerDto;
import com.assignment.entity.PetitionerEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SLPPAuthUser implements UserDetails{
    private final PetitionerEntity user;

    public SLPPAuthUser(PetitionerEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority(Boolean.TRUE.equals(user.isCommitteeAdmin()) ?  "ROLE_COMMITTEE": "ROLE_USER");
        return List.of(roleUser);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmailId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}