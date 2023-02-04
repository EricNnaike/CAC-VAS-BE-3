package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.Affiliates;
import com.example.cacvasbe.entities.Company;
import com.example.cacvasbe.entities.ProcessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AffiliatesRepository extends JpaRepository<Affiliates, Long> {
//    @Query(value = "SELECT * FROM affiliates WHERE company_fk = ?1 AND affiliate_type_fk = ?1", nativeQuery = true)
    List<Affiliates> findAffiliatesByCompanyFkAndAffiliateTypeFk(Long companyFk, Long affiliateTypeFk);
    List<Affiliates> findAffiliatesByCompanyFk(Long companyFk);
    List<Affiliates> findAffiliatesByAffiliateTypeFk(Long affiliateType);
    @Query(value = "select u from affiliates u where upper(u.surname) = :surname and upper(u.firstname) = :firstname and upper(u.other_name) = :other_name or upper(u.email) = :email", nativeQuery = true)
    List<Affiliates> findAllByFirstnameOrSurnameOrOtherNameOrEmail(String keyword);

//    @Query(value = "SELECT "+
//    "a.id as id, "+
//    "a.surname as surname, " +
//    "a.firstname as firstname "+
//    "FROM affiliates a " +
//    "where a.company_fk = :companyFk " +
//    "and a.affiliate_type_fk = :affiliateTypeFk ", nativeQuery = true)
//    List<Affiliates> findAffiliatesByCompanyFkAndAffiliateTypeFk(@Param("companyFk") Long companyFk, @Param("affiliateTypeFk") Long affiliateTypeFk);
//



}
