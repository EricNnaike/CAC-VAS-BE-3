package com.example.cacvasbe.controllers.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController2 {

//    private final UserManagementService userManagementService;

//    @PostMapping("/register")
//    public ResponseEntity<APIResponse<RegistrationResponse<?>>> register(@Valid @RequestParam("registration") String registration, @RequestParam("file")MultipartFile file) throws Exception {
//        UserDto userDto = new Gson().fromJson(registration, UserDto.class);
//        RegistrationResponse<?> response = userManagementService.register(userDto, file);
//        return new ResponseEntity<>(new APIResponse<>("Success", true, response), HttpStatus.CREATED);
//    }

//    @PutMapping("/update")
//    public ResponseEntity<APIResponse<UserDto>> update(@Valid @RequestParam("registration") String registration) throws Exception {
//        UserDto userDto = new Gson().fromJson(registration, UserDto.class);
//        UserDto response = userService.updateUser(userDto);
//        return new ResponseEntity<>(new APIResponse<>("Success", true, response), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<APIResponse<RegistrationDto>> updateUser(@Valid @RequestBody RegistrationDto registrationDto) throws Exception {
//        RegistrationDto response = userService.updateUser(registrationDto);
//        return new ResponseEntity<>(new APIResponse<>("Success", true, response), HttpStatus.CREATED);
//    }

}
