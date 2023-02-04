package com.example.cacvasbe.pojo;

import com.example.cacvasbe.entities.Role;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalUserResponse {
    private Long id;
    private String rcNumber;
    private String email;
    private LocalDate dateCreated;
    private Role role;
    private GenericStatusConstant status;
}
