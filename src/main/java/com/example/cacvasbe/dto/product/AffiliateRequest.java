package com.example.cacvasbe.dto.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AffiliateRequest {
    private String privateKey;
    private Long productId;
    private String rcNumber;
    private Long classificationFk;
}
