package com.example.cacvasbe.controllers.exceptions;

import com.example.cacvasbe.error_handler.*;
import com.example.cacvasbe.pojo.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<?> EntityAlreadyExistException(EntityAlreadyExistException ex) {
        return new ResponseEntity<>(new APIResponse<>(ex.getMessage(), false), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<?> CustomNotFoundException(CustomNotFoundException ex) {
        return new ResponseEntity<>(new APIResponse<>(ex.getMessage(), false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtInvalidException.class)
    public ResponseEntity<?> JwtInvalidException(JwtInvalidException ex) {
        return new ResponseEntity<>(new APIResponse<>(ex.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientRequestException.class)
    public ResponseEntity<?> ClientRequestException(ClientRequestException ex) {
        return new ResponseEntity<>(new APIResponse<>(ex.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFundException.class)
    public ResponseEntity<?> InsufficientFundException(InsufficientFundException ex) {
        return new ResponseEntity<>(new APIResponse<>(ex.getMessage(), false), HttpStatus.BAD_REQUEST);
    }
}
