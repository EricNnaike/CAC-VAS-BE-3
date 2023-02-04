package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.entities.Role;
import com.example.cacvasbe.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortalUserRepository extends JpaRepository<PortalUsers, Long> {
    Optional<PortalUsers> findByUsernameIgnoreCaseAndStatus(String username, GenericStatusConstant status);
    Optional<PortalUsers> findByUsernameAndRegistrationPaymentStatus(String email, RegistrationPaymentStatus registrationPaymentStatus);

    Optional<PortalUsers> findByIdAndStatus(Long id, GenericStatusConstant status );
    PortalUsers findByRoleAndStatus(RoleType roleType, GenericStatusConstant status);
    List<PortalUsers> findByStatusAndRequestForReactivation(GenericStatusConstant status, boolean request);
    PortalUsers findByUsernameIgnoreCase(String email);
    Optional<PortalUsers> findByUsernameIgnoreCaseAndRole(String username, Role role);

    Optional<PortalUsers> findUserByUsername(String email);
    Optional<PortalUsers> findByRcNumberAndStatus(String rcNumber, GenericStatusConstant status );
    Optional<PortalUsers> findByRcNumber(String rcNumber);
    PortalUsers findFirstByUsernameIgnoreCase(String email);
//    Optional<PortalUser> findPortalUserByRole(String username);

//    PortalUser findByPhoneNumber(String number);
//
//    PortalUser findFirstByPhoneNumber(String number);
}
