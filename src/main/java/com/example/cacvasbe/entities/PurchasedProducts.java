package com.example.cacvasbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "purchsed_products")
public class PurchasedProducts {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;

    private BigDecimal proposed_price;

    @Basic
    private LocalDateTime purchasedAt;
    @Basic
    private LocalDateTime lastUpdatedAt;

    @JsonIgnore
    @ManyToOne
    private ProductList products;

    @ManyToOne
    private LicensedPartners licensedPartner;

}
