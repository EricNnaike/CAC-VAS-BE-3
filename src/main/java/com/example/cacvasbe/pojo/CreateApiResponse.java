package com.example.cacvasbe.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateApiResponse {
    private String publicKey;
    private String privateKey;
}
