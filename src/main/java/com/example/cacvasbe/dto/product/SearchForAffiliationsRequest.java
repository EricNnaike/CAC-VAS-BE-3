package com.example.cacvasbe.dto.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchForAffiliationsRequest {
    private String privateKey;
    private Long productId;
    private String keyword;
}
