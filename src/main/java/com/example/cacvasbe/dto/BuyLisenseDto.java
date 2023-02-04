package com.example.cacvasbe.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class BuyLisenseDto {
//    @Pattern(regexp = "[+-]?[0-9][0-9]*")
    private BigDecimal amount;
}
