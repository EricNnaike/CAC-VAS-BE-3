package com.example.cacvasbe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class SelectProductResponse<T> {
    private String message;
    private boolean status;
    private Long id;
    private T data;
}
