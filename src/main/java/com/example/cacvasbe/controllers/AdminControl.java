package com.example.cacvasbe.controllers;

import com.example.cacvasbe.dto.*;
import com.example.cacvasbe.dto.registration.AdminDto;
import com.example.cacvasbe.entities.Role;
import com.example.cacvasbe.pojo.*;
import com.example.cacvasbe.repository.RoleRepository;
import com.example.cacvasbe.security.JwtService;
import com.example.cacvasbe.services.adminService.AdmintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AdminControl {

    private final AdmintService admintService;
    private final RoleRepository rolesRepository;
    private final JwtService jwtService;


    @GetMapping("/roles")
    public List<String> getRoles(){
        return admintService.getRoles();
    }




    @GetMapping("/deactivate-user/{id}")
    public HttpStatus deactivateUser(@PathVariable("id") Long id ) {
        admintService.deactivateUser(id);
        return HttpStatus.OK;
    }


    @GetMapping("/activate")
    public PortalUserPojo activateUser(@RequestParam Long user_id) {
       return admintService.activateUser(user_id);

    }

    @PostMapping("/create-admin")
    public PortalUserPojo creatAdmin(@RequestBody @Valid AdminDto dto) throws Exception {
        Role role = rolesRepository.findByNameIgnoreCase(dto.getRole()).orElseThrow(RuntimeException::new);
        return admintService.get(admintService.createAdmin(dto, jwtService.user, role));
    }

    @GetMapping(value = "/verification")
    public String verifyUser(@Valid @RequestParam("token") String token) throws Exception {
        admintService.verifyUser(token);
//        String link = "http://localhost:3000/login";
//        String link = "www.google.com";
        StringBuilder sb = new StringBuilder();
        sb.append("Email confirmed, follow the link below to login.");
//        sb.append("<br><br><a href=\"http://localhost:3000/login\" target=\"_blank\">Login</a>");

        String link = sb.toString();
//        return "Registered successfully, follow the link to login, <a href=\""+ link +"\" target=\"_blank\">Login</a>";
        return link;
    }

    @GetMapping("/users")
    public ResponseEntity<List<PortalUserResponse>> getListOfUsers() {
     List<PortalUserResponse> response = admintService.listOfUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/approve-license/{id}")
    public ResponseEntity<LicensePaymentApprovalResponse> apporveLicensePayment(@PathVariable("id") Long id) throws Exception {
        LicensePaymentApprovalResponse response = admintService.apporveLicensePayment(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/approve-regPayment/{id}")
    public ResponseEntity<?> apporveRegistrationPayment(@PathVariable("id") Long id) throws Exception {
        LicensePaymentApprovalResponse response = admintService.approveRegistrationPayment(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
