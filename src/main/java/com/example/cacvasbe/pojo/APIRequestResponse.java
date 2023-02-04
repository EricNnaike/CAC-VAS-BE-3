package com.example.cacvasbe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIRequestResponse<T> {
    private String message;
    private boolean status = false;
    private T data;
}
