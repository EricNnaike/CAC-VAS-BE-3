package com.example.cacvasbe.controllers.registration;

import com.example.cacvasbe.services.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/create")
public class RoleController {

    @Autowired
    private RoleService roleService;

//    @PostMapping(path = {"/role"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<RoleResponse<?>> crateRole(@RequestBody RoleDto roleDto) {
//        RoleResponse<?> response = roleService.createRole(roleDto);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
}
