package com.example.cacvasbe.error_handler;

public class ClientRequestException extends RuntimeException{
    public ClientRequestException(String message) {
        super(message);
    }
}
