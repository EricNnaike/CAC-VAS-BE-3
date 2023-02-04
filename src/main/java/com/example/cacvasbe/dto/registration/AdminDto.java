package com.example.cacvasbe.dto.registration;

import com.example.cacvasbe.enums.BusinessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto {
    private Long id;

    //    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Invalid Email Address")
    private String rcNumber;

    //    @Pattern(regexp = "^[A-Za-z|\\s]*$",message = "Invalid name")
    @Enumerated(EnumType.STRING)
    private BusinessType industry_type;

    //    @Pattern(regexp = "^[A-Za-z|\\s]*$",message = "Invalid name")
    private String description;

    //    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Invalid Email Address")
    private String username;

    //    @Pattern(regexp = "^[A-Za-z|\\s]*$",message = "Invalid name")
    private String password;

    private String role;
}
