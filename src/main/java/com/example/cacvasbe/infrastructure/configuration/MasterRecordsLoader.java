package com.example.cacvasbe.infrastructure.configuration;

import com.example.cacvasbe.dto.registration.AdminDto;
import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.entities.Role;
import com.example.cacvasbe.enums.BusinessType;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.enums.PermissionTypeConstant;
import com.example.cacvasbe.repository.PortalUserRepository;
import com.example.cacvasbe.services.adminService.AdmintService;
import com.example.cacvasbe.services.userService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterRecordsLoader {

    private final TransactionTemplate transactionTemplate;
    private final AdmintService admintService;
    private final UserService userService;
    private final PortalUserRepository portalUserRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        log.info("STARTING...........");
        transactionTemplate.execute(tx -> {
            try {
                loadDefaults();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    private void loadDefaults(){

        List<PermissionTypeConstant> permissionTypeConstantList =  new ArrayList<>();
        permissionTypeConstantList.add(PermissionTypeConstant.CREATE_USER);
        permissionTypeConstantList.add(PermissionTypeConstant.UPDATE_USER);
        permissionTypeConstantList.add(PermissionTypeConstant.SET_PRICE);
        permissionTypeConstantList.add(PermissionTypeConstant.DELETE_USER);

        List<PermissionTypeConstant> permissionTypeConstantList1 =  new ArrayList<>();
        permissionTypeConstantList.add(PermissionTypeConstant.DEFAULT);

//        List<PermissionTypeConstant> permissionTypeConstantList2 =  new ArrayList<>();
//        permissionTypeConstantList.add(PermissionTypeConstant.MANAGE_DRIVER);

        List<PermissionTypeConstant> permissionTypeConstantList3 =  new ArrayList<>();
        permissionTypeConstantList.add(PermissionTypeConstant.APPROVE_USER);
        permissionTypeConstantList.add(PermissionTypeConstant.ACTIVATE_USER);

        Role role = admintService.createRole("SUPER_ADMIN",permissionTypeConstantList);
        Role role1 = admintService.createRole("GENERAL_USER",permissionTypeConstantList1);
        Role role3 = admintService.createRole( "ADMIN", permissionTypeConstantList3);

        AdminDto dto = new AdminDto();
        dto.setRcNumber("VAS");
        dto.setDescription("Supper Admin");
        dto.setUsername("vas@oasismgt.net");
        dto.setPassword("password");
        dto.setIndustry_type(BusinessType.BUSINESS_NAME);
        createSuperAdminUser(dto, role);

        AdminDto adminDto = new AdminDto();
        adminDto.setRcNumber("CRO");
        adminDto.setDescription("Admin");
        adminDto.setUsername("cro@oasismgt.net");
        adminDto.setPassword("password");
        dto.setIndustry_type(BusinessType.BUSINESS_NAME);
        createAdminUser(adminDto, role3);

        AdminDto adminDto1 = new AdminDto();
        adminDto1.setRcNumber("CRCD");
        adminDto1.setDescription("Admin");
        adminDto1.setUsername("src@oasismgt.net");
        adminDto1.setPassword("password");
        dto.setIndustry_type(BusinessType.BUSINESS_NAME);
        createAdminUser(adminDto1, role3);


    }

    private void createSuperAdminUser(AdminDto dto, Role role) {
        System.out.println(dto);
        portalUserRepository.findByUsernameIgnoreCaseAndStatus(dto.getUsername(), GenericStatusConstant.ACTIVE)
                .orElseGet(() -> {
                    log.info("===========CREATING SUPER_ADMIN {} ============", dto.getUsername());
                    try {
                       PortalUsers portalUsers = admintService.createAdmin(dto, null, role);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    private void createAdminUser(AdminDto dto, Role role) {
        portalUserRepository.findByUsernameIgnoreCaseAndStatus(dto.getUsername(), GenericStatusConstant.ACTIVE)
                .orElseGet(() -> {
                    log.info("===========CREATING ADMIN {} ============", dto.getUsername());
                    try {
                        PortalUsers portalUsers = admintService.createAdmin(dto, null, role);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }



}
