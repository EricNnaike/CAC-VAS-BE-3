package com.example.cacvasbe.controllers;

import com.example.cacvasbe.dto.*;
import com.example.cacvasbe.dto.registration.UserDto;
import com.example.cacvasbe.entities.Role;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.pojo.*;
import com.example.cacvasbe.repository.RoleRepository;
import com.example.cacvasbe.security.JwtService;
import com.example.cacvasbe.services.adminService.AdmintService;
import com.example.cacvasbe.services.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "**")
public class UserController {

    @Autowired
    private  UserService userService;
    @Autowired
    private RoleRepository rolesRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AdmintService admintService;


    @PostMapping("/create")
    public ResponseEntity<APIResponse<PortalUserPojo>> createUser(@RequestBody UserDto dto) throws Exception {
        System.out.println(dto);
        Role role = rolesRepository.findByNameIgnoreCase(dto.getRole()).orElseThrow(RuntimeException::new);
        PortalUserPojo response = userService.get(userService.createUser(dto, jwtService.user, role));
        return new ResponseEntity<>(new APIResponse<>("Check your mail to verify your email count", true, response), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponse>> authenticateUser(@RequestBody LoginRequestDto dto) throws Exception {
        System.out.println("dto........" +dto);
        LoginResponse response = userService.authenticateUser(dto);
        return new ResponseEntity<>(new APIResponse<>("success", true, response), HttpStatus.OK);

    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordDto dto) throws Exception {
        userService.resetPassword(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) throws MessagingException {
        return userService.forgotPassword(request);
    }

    @PostMapping("/request-reactivation")
    public ResponseEntity<ApprovalResponse> requestReactivation(@RequestParam Long user_id) {
        return new ResponseEntity<>(userService.requestReactivation(user_id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/user-data/{rcNumber}")
    public ResponseEntity<?> getUser (@PathVariable String rcNumber) {
        ResponseClass response = userService.getUserByRCNumber(rcNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/deactivate")
    public HttpStatus deactivateUser(@RequestBody DeactivateUserDto deactivateUserDto) {
        userService.deactivateUser(deactivateUserDto);
        return HttpStatus.OK;
    }

    @GetMapping("/status/{rcnumber}")
    public GenericStatusConstant getUserStatus(@PathVariable("rcnumber") String rcNumber) {
        return userService.findStatus(rcNumber);
    }

    @GetMapping("/verifyRCNumber/{rcNumber}")
    public boolean verifyRCNumber(@PathVariable("rcNumber") String rcNumber) {
        return userService.cacService(rcNumber);
    }

    @GetMapping("/logout")
    public APIResponse<?> logout() {
        return userService.logout();
    }

//    @GetMapping("/logout")
//    public APIResponse<?> logout( @RequestHeader("Authorization") String token) {
//        return userService.logout(token);
//    }

    @GetMapping("/buy-license")
    public ResponseEntity<?> buyLicense() throws Exception {
        System.out.println("called");
        return new ResponseEntity<>(userService.buyLisense(), HttpStatus.OK);
    }

    @GetMapping("/createKeys")
    public ResponseEntity<String> createPrivatePublickey() {
        return new ResponseEntity<>(userService.createPublicApiKey(), HttpStatus.OK);
    }

    @GetMapping("/api-keys")
    public ResponseEntity<CreateApiResponse> getApiKeys() {
        return new ResponseEntity<>(userService.getApiKey(), HttpStatus.OK);
    }

}
