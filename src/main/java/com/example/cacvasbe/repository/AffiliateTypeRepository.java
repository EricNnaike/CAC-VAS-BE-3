package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.AffiliateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AffiliateTypeRepository extends JpaRepository<AffiliateType, Long> {
    Optional<AffiliateType> findAffiliateTypeById(Long id);
}
