package com.example.cacvasbe.pojo;

import com.example.cacvasbe.entities.cacdata.CACData;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseClass<T> {
    private String status;
    private boolean success;
    private String errorCode;
    private CACData data;
    private String message;
    private LocalDateTime currentTime;
}
