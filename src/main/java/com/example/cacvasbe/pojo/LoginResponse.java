package com.example.cacvasbe.pojo;

import com.example.cacvasbe.enums.PermissionTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class LoginResponse {
    private Long id;
    private String email;
    private String rcNumber;
    private String token;
    private String role;
    private List<PermissionTypeConstant> permissions;
    private String message;

    public LoginResponse(String message) {
        this.message = message;
    }

    public LoginResponse(Long id, String email, String rcNumber, String token, String role, List<PermissionTypeConstant> permissions, String message) {
        this.id = id;
        this.email = email;
        this.rcNumber = rcNumber;
        this.token = token;
        this.role = role;
        this.permissions = permissions;
        this.message = message;
    }

    public LoginResponse(Long id, String email, String rcNumber, String token, String role, List<PermissionTypeConstant> permissions) {
        this.id = id;
        this.email = email;
        this.rcNumber = rcNumber;
        this.token = token;
        this.role = role;
        this.permissions = permissions;
    }

    public LoginResponse() {

    }
}
