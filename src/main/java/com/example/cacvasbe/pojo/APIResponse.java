package com.example.cacvasbe.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class APIResponse<T> {
    private String message;
    private boolean status = false;
    private LocalDateTime timeCreated = LocalDateTime.now();
    private T data;

    public APIResponse(String message, boolean status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public APIResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }
}
