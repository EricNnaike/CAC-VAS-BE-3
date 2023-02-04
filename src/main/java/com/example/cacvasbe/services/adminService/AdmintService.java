package com.example.cacvasbe.services.adminService;

import com.example.cacvasbe.dto.*;
import com.example.cacvasbe.dto.registration.AdminDto;
import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.entities.Role;
import com.example.cacvasbe.enums.PermissionTypeConstant;
import com.example.cacvasbe.pojo.*;

import java.util.List;

public interface AdmintService {
    PortalUsers createAdmin(AdminDto user, PortalUsers createdBy, Role role) throws Exception;
    void deactivateUser(Long id);
    PortalUserPojo activateUser(Long userId);
    Role createRole(String name, List<PermissionTypeConstant> permissionTypeConstants);
//    ApprovalResponse approveUserPayment(PortalUser);

    PortalUserPojo get(PortalUsers user);
    List<PortalUserPojo> get(List<PortalUsers> users);
    List<String> getRoles();
    Object verifyUser(String userToken);
    List<PortalUserResponse> listOfUsers();
    PortalUserPojo getListOfDeactivatedUsers(Long id);
//    PortalUserPojo getDeactivatedUser(PortalUser user);
    LicensePaymentApprovalResponse apporveLicensePayment(Long id) throws Exception;
    LicensePaymentApprovalResponse approveRegistrationPayment(Long id) throws Exception;




}
