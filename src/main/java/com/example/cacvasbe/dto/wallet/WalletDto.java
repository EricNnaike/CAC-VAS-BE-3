package com.example.cacvasbe.dto.wallet;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class WalletDto {
    private BigDecimal currentBalance;
}
