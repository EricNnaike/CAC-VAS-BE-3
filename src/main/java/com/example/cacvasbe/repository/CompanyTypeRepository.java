package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {
    Optional<CompanyType> findCompanyTypeByName(String name);
    @Query(value = "SELECT * FROM company WHERE name = ?1", nativeQuery = true)
    Optional<CompanyType> findCompanyTypeByName(List<CompanyType> companyTypeName);
    List<CompanyType> findCompanyTypeByClassificationFk(Long classificationFk);

}
