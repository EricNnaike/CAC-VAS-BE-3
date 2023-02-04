package com.example.cacvasbe.dto.registration;

import com.example.cacvasbe.entities.Role;
import com.example.cacvasbe.enums.BusinessType;
import com.example.cacvasbe.enums.Gender;
import com.example.cacvasbe.enums.RoleType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Set;

@Data
public class UserDto {


//    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Invalid Email Address")
    private String rcNumber;

//    @Pattern(regexp = "^[A-Za-z|\\s]*$",message = "Invalid name")
//    @Enumerated(EnumType.STRING)
    private BusinessType industry_type;

//    @Pattern(regexp = "^[A-Za-z|\\s]*$",message = "Invalid name")
    private String natureOfBusiness;

//    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Invalid Email Address")
    private String email;

//    @Pattern(regexp = "^[A-Za-z|\\s]*$",message = "Invalid name")
    private String password;

//    @Pattern(regexp = "((^(234){1}[0–9]{10})|((^234)[0–9]{10})|((^0)(7|8|9){1}(0|1){1}[0–9]{8}))")
    private String nin;

    private String companyProfile;

    private String cacCertificate;

    private String picture;

    private String role;



}
