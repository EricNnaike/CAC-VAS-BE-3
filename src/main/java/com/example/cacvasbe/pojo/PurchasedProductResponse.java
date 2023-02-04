package com.example.cacvasbe.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchasedProductResponse {
    private String name;
    private String description;
    private Long id;
    private BigDecimal price;
}
