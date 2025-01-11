package com.assignment.entity;

import javax.persistence.*;

@Table
@Entity(name = "petition_signing_users")
public class PetitionSigningUserEntity {

    @EmbeddedId
    private PetitionSigningUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petition_id", insertable = false, updatable = false)
    private PetitionEntity petition;

    // Getters and setters
    public PetitionSigningUserId getId() {
        return id;
    }

    public void setId(PetitionSigningUserId id) {
        this.id = id;
    }

    public PetitionEntity getPetition() {
        return petition;
    }

    public void setPetition(PetitionEntity petition) {
        this.petition = petition;
    }

}
