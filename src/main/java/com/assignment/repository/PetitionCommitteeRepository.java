package com.assignment.repository;

import com.assignment.entity.PetitionCommitteeEntity;
import com.assignment.entity.PetitionerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PetitionCommitteeRepository extends JpaRepository<PetitionCommitteeEntity, UUID> {

    PetitionCommitteeEntity findPetitionCommitteeByEmailId(String emailId);
}
