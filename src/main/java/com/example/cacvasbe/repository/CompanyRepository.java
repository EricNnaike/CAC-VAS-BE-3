package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.Company;
import com.example.cacvasbe.entities.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findCompanyByRcNumber(String rcNumber);
    @Query(value = "SELECT * FROM company WHERE rc_number = :rcNumber and company_type_fk in :companyTypeFk", nativeQuery = true)
    Optional<Company> findAllByCompanyByRcNumberAndCompanyTypeFk(String rcNumber, List<CompanyType> companyTypeFk);
    @Query(value = "SELECT * FROM company WHERE rc_number = :rcNumber and company_type_fk = :companyTypeFk", nativeQuery = true)
    Company findAllByCompanyByRcNumberAndCompanyTypeFk(String rcNumber, Long companyTypeFk);

}
