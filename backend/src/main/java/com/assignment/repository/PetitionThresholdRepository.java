package com.assignment.repository;

import com.assignment.entity.PetitionThresholdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionThresholdRepository extends JpaRepository<PetitionThresholdEntity, Integer> {
}
