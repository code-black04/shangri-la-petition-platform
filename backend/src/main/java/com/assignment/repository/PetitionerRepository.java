package com.assignment.repository;

import com.assignment.entity.PetitionerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PetitionerRepository extends JpaRepository<PetitionerEntity, UUID> {

    PetitionerEntity findPetitionerByEmailId(String emailId);

    PetitionerEntity findPetitionerByBiometricId(String biometricId);

}
