package com.example.cacvasbe.dto.wallet;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundWalletRequest {
    private BigDecimal amount;
}
