package com.example.cacvasbe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundWalletResponse {
    private String name;
    private BigDecimal amount;
    private String email;
}
