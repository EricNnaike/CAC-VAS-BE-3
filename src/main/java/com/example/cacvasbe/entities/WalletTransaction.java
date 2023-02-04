package com.example.cacvasbe.entities;

import com.example.cacvasbe.enums.PaymentChannel;
import com.example.cacvasbe.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WalletTransaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

//    @NotNull(message = "amount field is empty")
    private BigDecimal amount;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    private String description;

    private String reference;

    private String email;

    private PaymentChannel paymentChannel;

//    @Column(
//            nullable = false,
//            columnDefinition = "NUMERIC(11,2) DEFAULT 0.0"
//    )
//    private BigDecimal totalTransValue = new BigDecimal(0.00);

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime modifiedTime;

    @JsonIgnore
    @ManyToOne
    private PortalUsers username;
}
