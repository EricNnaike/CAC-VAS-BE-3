package com.example.cacvasbe.error_handler;

public class InsufficientFundException extends RuntimeException{
    public InsufficientFundException(String message) {
        super(message);
    }
}