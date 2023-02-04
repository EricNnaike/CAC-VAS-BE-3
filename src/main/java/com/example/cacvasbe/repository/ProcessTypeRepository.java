package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.ProcessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessTypeRepository extends JpaRepository<ProcessType, Long> {
    Optional<ProcessType> findProcessTypeById(Long id);
}
