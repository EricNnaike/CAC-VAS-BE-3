package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.LicensedPartners;
import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.enums.LicensePaymentStatus;
import com.example.cacvasbe.enums.UserAccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LicensePartnerRepository extends JpaRepository<LicensedPartners, Long> {
    Optional<LicensedPartners> findLicensedPartnersByRcNumberAndPaymentStatus(String reNumber, LicensePaymentStatus status);
    Optional<LicensedPartners> findLicensedPartnersByIdAndStatus(Long id, GenericStatusConstant status);
    Optional<LicensedPartners> findLicensedPartnersByUserAndPaymentStatus(PortalUsers user, LicensePaymentStatus paymentStatus);
    Optional<LicensedPartners> findLicensedPartnersByRcNumberAndAccountStatus(String rcNumber, UserAccountStatus accountStatus);
    Optional<LicensedPartners> findLicensedPartnersByUsernameAndAccountStatus(String username, UserAccountStatus accountStatus);
    Optional<LicensedPartners> findLicensedPartnersByIdAndAccountStatus(Long id, UserAccountStatus accountStatus);



}
