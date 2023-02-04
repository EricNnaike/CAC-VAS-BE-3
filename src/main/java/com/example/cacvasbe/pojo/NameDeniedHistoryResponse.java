package com.example.cacvasbe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameDeniedHistoryResponse {
    private String option1;
    private String option2;
    private String reasonForDenial;
    private LocalDateTime dateDenied;
}
