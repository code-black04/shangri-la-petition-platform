package com.assignment.repository;

import com.assignment.entity.PetitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionRepository extends JpaRepository<PetitionEntity, Integer> {
}
