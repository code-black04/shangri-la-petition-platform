package com.assignment.entity;

import javax.persistence.*;

@Table
@Entity(name = "petition_signing_users")
public class PetitionSigningUserEntity {

    @EmbeddedId
    private PetitionSigningUserId id;

    public PetitionSigningUserId getId() {
        return id;
    }

    public void setId(PetitionSigningUserId id) {
        this.id = id;
    }


}
