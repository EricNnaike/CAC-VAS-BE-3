package com.example.cacvasbe.entities;

import com.example.cacvasbe.enums.LicensePaymentStatus;
import com.example.cacvasbe.enums.UserAccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "licensed_partners"
)
public class LicensedPartners extends StatusEntity{

    @Basic(
            optional = false
    )
    @Column(
            name = "rc_number",
            table = "licensed_partners",
            nullable = false,
            length = 1024
    )
    private String rcNumber;
    private String username;

    @Basic
    @Column(
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    protected LicensePaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private UserAccountStatus accountStatus;

    @Basic
    private LocalDateTime licenseExpiryDate;

    @Basic
    private LocalDateTime licenseRenewedDate;

    @Basic
    @Column(
            name = "public_key",
            table = "licensed_partners"
    )
    private String publicKey;

    @Basic
    @Column(
            name = "private_key",
            table = "licensed_partners"
    )
    private String privateKey;

    @JsonIgnore
    @OneToOne
    private PortalUsers user;


}
