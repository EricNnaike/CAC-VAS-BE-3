package com.example.cacvasbe.controllers;

import com.example.cacvasbe.services.remita.service.RemitaBillingGatewayService;
import lombok.RequiredArgsConstructor;
import ng.com.systemspecs.remitabillinggateway.generaterrr.GenerateResponse;
import ng.com.systemspecs.remitabillinggateway.validate.ValidateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RemitaController {

    private final RemitaBillingGatewayService remitaBillingGatewayService;

    @PostMapping("/generate-rrr")
    public ResponseEntity<GenerateResponse> generateRRR(@RequestBody @Valid ValidateRequest validateRequest) {
        GenerateResponse response = remitaBillingGatewayService.generateRRR(validateRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
