package com.example.cacvasbe.entities;

//import com.example.cacvasbe.entities.enums.Gender;
import com.example.cacvasbe.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(

        name = "portal_users"
)
public class PortalUsers extends StatusEntity{
    @Basic(
            optional = false
    )
    @Column(
            name = "rc_number",
            table = "portal_users",
            nullable = false,
            length = 1024,
            unique = true
    )
    private String rcNumber;

    @Basic(
            optional = false
    )
    @Column(
            name = "industry_type",
            table = "portal_users",
            nullable = false,
            length = 1024
    )
    @Enumerated(EnumType.STRING)
    private BusinessType industry_type;


    @Enumerated(EnumType.STRING)
    private Payment paymentType;

    @Basic
    @Column(
            name = "description",
            table = "portal_users",
            nullable = false,
            length = 1024
    )
    private String description;
    @Basic(
            optional = false
    )
    @Column(
            name = "username",
            table = "portal_users",
//            length = 1024,
            unique = true
    )
    private String username;

    @Basic
    @Column(
            name = "generated_password",
            table = "portal_users",
            length = 1024
    )
    private String generatedPassword;

    @Basic
    @Column(
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    protected RegistrationPaymentStatus registrationPaymentStatus;

    @Column(columnDefinition = "boolean default false")
    private boolean emailVerified;

    private String nationalIdentificationNumber;

    private String companyProfile;

    private String cacCertificate;

    private String picture;


    @Column(columnDefinition = "boolean default false")
    private boolean requestForReactivation;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private Role role;


    @JsonManagedReference
    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WalletTransaction> transactionList = new ArrayList<>();



}