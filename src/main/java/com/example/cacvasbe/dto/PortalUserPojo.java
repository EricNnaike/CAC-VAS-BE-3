package com.example.cacvasbe.dto;

import com.example.cacvasbe.entities.Role;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.enums.Payment;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PortalUserPojo {
    private Long id;
    private String rcNumber;
    private String email;
    private LocalDate dateCreated;
    private Role role;
    private GenericStatusConstant status;
    private Payment payment;
}