package com.example.cacvasbe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class MakePaymentResponse {
    private boolean status;
    private String message;
    private LocalDateTime createdAt;
}
