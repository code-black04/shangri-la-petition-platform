package com.assignment.repository;

import com.assignment.dto.PetitionStatusEnum;
import com.assignment.entity.PetitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetitionRepository extends JpaRepository<PetitionEntity, Integer> {

    PetitionEntity findByPetitionId(Integer petitionId);

    List<PetitionEntity> findAllByPetitionStatusEnum(PetitionStatusEnum status);
}
