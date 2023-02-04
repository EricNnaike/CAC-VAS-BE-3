package com.example.cacvasbe.dto.wallet;

import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletWithdrawalRequest {

    private String username;
    private BigDecimal amount;
}
