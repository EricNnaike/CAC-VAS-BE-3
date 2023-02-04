package com.example.cacvasbe.services.adminService;


import com.example.cacvasbe.dto.*;
import com.example.cacvasbe.dto.email.EmailDetailsDto;
import com.example.cacvasbe.dto.registration.AdminDto;
import com.example.cacvasbe.email.ConfirmationToken;
import com.example.cacvasbe.entities.*;
import com.example.cacvasbe.entities.Permission;
import com.example.cacvasbe.enums.*;
import com.example.cacvasbe.error_handler.CustomNotFoundException;
import com.example.cacvasbe.error_handler.EntityAlreadyExistException;
import com.example.cacvasbe.pojo.*;
import com.example.cacvasbe.repository.*;
import com.example.cacvasbe.security.JwtService;
import com.example.cacvasbe.security.PasswordService;
import com.example.cacvasbe.security.UserDetails;
import com.example.cacvasbe.services.emailService.ConfirmationTokenService;
import com.example.cacvasbe.services.sequence.portal_user_id.PortalUserSequenceService;
import com.example.cacvasbe.utils.cloudinary.CloudinaryService;
import com.example.cacvasbe.utils.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdmintServiceImp implements AdmintService {

    private final CloudinaryService cloudinaryService;

    private final PortalUserSequenceService portalUserSequenceService;

//    private final ConfirmationTokenService confirmationTokenService;
    private final RoleRepository roleRepository;
    private final PortalUserRepository portalUserRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordService passwordService;
    private final JwtService jwtService;
    private final ConfirmationTokenService confirmationTokenService;
    private final LicensePartnerRepository licensePartnerRepository;
    private final EmailService emailService;



    @Override
    @Transactional
    public Role createRole(String name, List<PermissionTypeConstant> permissionTypeConstants) {
        return roleRepository.findByNameIgnoreCase(name).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName(name);
            roleRepository.save(newRole);
            createPermission(permissionTypeConstants, newRole);
            return newRole;
        });
    }

    @Override
    public List<String> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }


    private void createPermission(List<PermissionTypeConstant> permissionTypeConstants, Role role) {
        permissionTypeConstants.forEach(permissionTypeConstant -> {
            Permission permission = new Permission();
            permission.setPermissionTypeConstant(permissionTypeConstant);
            permission.setRole(role);
            permissionRepository.save(permission);
        });
    }


    private List<PermissionTypeConstant> getPermission(Role role){
        List<Permission> permissions = permissionRepository.findAllByRole(role);

        List<PermissionTypeConstant> permissionTypeConstantList = new ArrayList<>();

        permissions.forEach(permission -> {
            permissionTypeConstantList.add(permission.getPermissionTypeConstant());
        });

        return permissionTypeConstantList;
    }

    @Override
    public PortalUserPojo get(PortalUsers user) {
        PortalUserPojo pojo = new PortalUserPojo();
        pojo.setRcNumber(user.getRcNumber());
        pojo.setEmail(user.getUsername());
        pojo.setDateCreated(user.getCreatedAt().toLocalDate());
        pojo.setRole(user.getRole());
        pojo.setStatus(user.getStatus());
        pojo.setId(user.getId());

        return pojo;
    }


    @Override
    public List<PortalUserPojo> get(List<PortalUsers> users) {
        return users.stream().map(user -> {

            PortalUserPojo pojo = new PortalUserPojo();
            pojo.setRcNumber(user.getRcNumber());
            pojo.setEmail(user.getUsername());
            pojo.setDateCreated(user.getCreatedAt().toLocalDate());
            pojo.setRole(user.getRole());
            pojo.setStatus(user.getStatus());
            pojo.setId(user.getId());

            return pojo;
        }).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public PortalUserPojo activateUser(Long userId) {
        String username = UserDetails.getLoggedInUserDetails();

        Optional<PortalUsers> admin = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);
        if (admin.isEmpty()) {
            throw new CustomNotFoundException("User is not found");
        }

        System.out.println("role........... "+admin.get().getRole());

        List<String> role = this.getRoles();
        for (String itr: role) {
            if (!itr.equals("ADMIN")) {
                throw new CustomNotFoundException("User is not an Admin");
            }
        }

        if (admin.get().getRole().getName().equals("ADMIN")) {
            PortalUsers portalUsers = portalUserRepository.findByIdAndStatus(userId, GenericStatusConstant.INACTIVE).get();

                LocalDateTime now = LocalDateTime.now();
                portalUsers.setStatus(GenericStatusConstant.ACTIVE);
                portalUsers.setRequestForReactivation(false);
                portalUsers.setLastUpdatedAt(now);
                portalUserRepository.save(portalUsers);

            PortalUserPojo portalUserPojo = new PortalUserPojo();
            portalUserPojo.setRcNumber(portalUsers.getRcNumber());
            portalUserPojo.setId(portalUsers.getId());
            portalUserPojo.setEmail(portalUsers.getUsername());
            portalUserPojo.setDateCreated(portalUsers.getCreatedAt().toLocalDate());
            portalUserPojo.setRole(portalUsers.getRole());
            portalUserPojo.setStatus(portalUsers.getStatus());
            return portalUserPojo;
        }
        throw new CustomNotFoundException("User not an admin");

    }


    @Override
    public Object verifyUser(String userToken) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(userToken);
        if (confirmationToken == null) {
            throw new CustomNotFoundException("token not found");
        }
        if (confirmationToken.getConfirmedAt() != null) {
            throw new EntityAlreadyExistException("email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new CustomNotFoundException("token expired");
        }
        confirmationTokenService.setConfirmedAt(userToken);
        confirmationToken.getUsers().setEmailVerified(true);
        return "confirmed";
    }

    @Override
    public PortalUserPojo getListOfDeactivatedUsers(Long id) {
//        PortalUser user = portalUserRepository.findByIdAndStatus(id, GenericStatusConstant.ACTIVE).get();
//        PortalUserPojo portalUserPojo = new PortalUserPojo();
//        portalUserPojo.setRcNumber(user.getRcNumber());
//        portalUserPojo.setId(user.getId());
//        portalUserPojo.setEmail(user.getUsername());
//        portalUserPojo.setDateCreated(user.getCreatedAt().toLocalDate());
//        portalUserPojo.setRole(user.getRole());
//        portalUserPojo.setStatus(GenericStatusConstant.INACTIVE);
        return null;
    }


    @Override
    public PortalUsers createAdmin(AdminDto user, PortalUsers createdBy, Role role) throws Exception {
        PortalUsers portalUsers = new PortalUsers();
        portalUsers.setCreatedAt(LocalDateTime.now());
        portalUsers.setUsername(user.getUsername());
        portalUsers.setRcNumber(user.getRcNumber());
        portalUsers.setStatus(GenericStatusConstant.ACTIVE);
        portalUsers.setGeneratedPassword(passwordService.hashPassword(user.getPassword()));
        portalUsers.setDescription(user.getDescription());
        portalUsers.setRole(role);
        portalUsers.setRegistrationPaymentStatus(RegistrationPaymentStatus.APPROVED);
//        portalUser.setCreatedBy(portalUser);
        portalUsers.setIndustry_type(BusinessType.BUSINESS_NAME);

        portalUserRepository.save(portalUsers);
        return portalUsers;
    }


    @Override
    public List<PortalUserResponse> listOfUsers() {
        String username = UserDetails.getLoggedInUserDetails();
        Optional<PortalUsers> admin = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);
        if (admin.isEmpty()) {
            throw new CustomNotFoundException("User is not found");
        }
        if (!admin.get().getRole().getName().equals("ADMIN")) {
            throw new CustomNotFoundException("User has no authority over this endpoind");
        }
        List<PortalUsers> portalUsers = portalUserRepository.findAll();
        return portalUsers.stream().map(users -> {
            PortalUserResponse portalUserPojo = new PortalUserResponse();
            portalUserPojo.setStatus(users.getStatus());
            portalUserPojo.setEmail(users.getUsername());
            portalUserPojo.setRole(users.getRole());
            portalUserPojo.setId(users.getId());
            portalUserPojo.setDateCreated(users.getCreatedAt().toLocalDate());
            portalUserPojo.setRcNumber(users.getRcNumber());

            return portalUserPojo;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deactivateUser(Long id) {

        String username = UserDetails.getLoggedInUserDetails();
        Optional<PortalUsers> admin = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);
        if (admin.isEmpty()) {
            throw new CustomNotFoundException("User is not found");
        }
        if (!admin.get().getRole().getName().equals("ADMIN")) {
            throw new CustomNotFoundException("User has no authority over this endpoind");
        }

        PortalUsers portalUsers = portalUserRepository.findById(id).get();
        LocalDateTime now = LocalDateTime.now();

        portalUsers.setStatus(GenericStatusConstant.INACTIVE);
        portalUsers.setLastUpdatedAt(now);
        portalUsers.setDeactivatedBy(admin.get());
        portalUserRepository.save(portalUsers);
    }


    @Override
    public LicensePaymentApprovalResponse apporveLicensePayment(Long id) throws Exception {
        String username = UserDetails.getLoggedInUserDetails();
        Optional<PortalUsers> admin = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);
        if (admin.isEmpty()) return new LicensePaymentApprovalResponse("Invalid User", admin.get().getId());

        if (!admin.get().getRole().getName().equals("ADMIN")) {
            return new LicensePaymentApprovalResponse("User is not an admin", admin.get().getId());
        }

        LicensedPartners licensedPartners = licensePartnerRepository.findLicensedPartnersByIdAndStatus(id, GenericStatusConstant.ACTIVE)
                .orElseThrow(() -> new CustomNotFoundException("Invalid Licensed Partner"));

        licensedPartners.setPaymentStatus(LicensePaymentStatus.APPROVED);
        licensedPartners.setLastUpdatedAt(LocalDateTime.now());
        licensedPartners.setLicenseExpiryDate(LocalDateTime.now());
        licensedPartners.setAccountStatus(UserAccountStatus.VERIFIED);
        licensePartnerRepository.save(licensedPartners);

        EmailDetailsDto emailDetailsRequest = new EmailDetailsDto();
        emailDetailsRequest.setMsgBody(emailService.sendLicenseNotification(licensedPartners.getUsername()));
        emailDetailsRequest.setSubject("CAC Email");
        emailDetailsRequest.setRecipient(licensedPartners.getUsername());
        emailService.sendMailWithAttachment(emailDetailsRequest);

        return new LicensePaymentApprovalResponse("APPROVED", licensedPartners.getId());
    }

    @Override
    public LicensePaymentApprovalResponse approveRegistrationPayment(Long id) throws Exception {
        String username = UserDetails.getLoggedInUserDetails();
        Optional<PortalUsers> admin = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);
        if (admin.isEmpty()) return new LicensePaymentApprovalResponse("Invalid User", admin.get().getId());

        if (!admin.get().getRole().getName().equals("ADMIN")) {
            return new LicensePaymentApprovalResponse("User is not an admin", admin.get().getId());
        }
        PortalUsers portalUsers = portalUserRepository.findByIdAndStatus(id, GenericStatusConstant.ACTIVE)
                .orElseThrow(() -> new CustomNotFoundException("Invalid Portal User"));
        portalUsers.setRegistrationPaymentStatus(RegistrationPaymentStatus.APPROVED);
        portalUsers.setLastUpdatedAt(LocalDateTime.now());
        portalUserRepository.save(portalUsers);

        EmailDetailsDto emailDetailsRequest = new EmailDetailsDto();
        emailDetailsRequest.setMsgBody(emailService.sendRegNotification(portalUsers.getUsername()));
        emailDetailsRequest.setSubject("CAC Email");
        emailDetailsRequest.setRecipient(portalUsers.getUsername());
        emailService.sendMailWithAttachment(emailDetailsRequest);

        return new LicensePaymentApprovalResponse("APPROVED", portalUsers.getId());
    }




}
