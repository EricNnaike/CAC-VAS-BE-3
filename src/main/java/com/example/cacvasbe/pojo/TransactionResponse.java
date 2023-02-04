package com.example.cacvasbe.pojo;

import com.example.cacvasbe.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    private TransactionType transactionType;
    private String description;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private Long usertId;
    private String email;
    private Long transactionId;
    private String rcNumber;

    public TransactionResponse(BigDecimal amount, Long usertId, String email, String rcNumber) {
        this.amount = amount;
        this.usertId = usertId;
        this.email = email;
        this.rcNumber = rcNumber;
    }

    public TransactionResponse(TransactionType transactionType, String description, BigDecimal amount, LocalDateTime createdAt, Long usertId, String email, Long transactionId, String rcNumber) {
        this.transactionType = transactionType;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
        this.usertId = usertId;
        this.email = email;
        this.transactionId = transactionId;
        this.rcNumber = rcNumber;
    }

    public TransactionResponse() {
    }
}
