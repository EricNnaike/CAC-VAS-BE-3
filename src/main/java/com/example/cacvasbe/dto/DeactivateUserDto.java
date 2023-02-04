package com.example.cacvasbe.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeactivateUserDto {
    private String reasonForDeactivation;
}
